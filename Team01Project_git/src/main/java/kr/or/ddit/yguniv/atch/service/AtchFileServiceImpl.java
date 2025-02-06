package kr.or.ddit.yguniv.atch.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.Failable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.atch.dao.AtchFileMapper;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;

@Service
public class AtchFileServiceImpl implements AtchFileService {

	@Autowired
	private AtchFileMapper mapper;

	@Override
	public void createAtchFile(AtchFileVO atchFile, File saveFolder) {
		Optional.of(atchFile)
				.map(AtchFileVO::getFileDetails)
				.ifPresent(fds -> 
					fds.forEach(
						Failable.asConsumer(fd -> fd.uploadFileSaveTo(saveFolder))
					)
				);
		mapper.insertAtchFile(atchFile);
	}

	/**
	 * 
	 * 파일 메타데이터와 2진 데이터 결합
	 * 
	 * @param fileDetail
	 * @param saveFolder
	 */
	private void mergeMetadAndBinaryData(AtchFileDetailVO fileDetail, File saveFolder) {
		FileSystemResource savedFile = new FileSystemResource(new File(saveFolder, fileDetail.getStreFileNm()));
		fileDetail.setSavedFile(savedFile);
	}

	@Override
	public AtchFileVO readAtchFile(int atchFileId, boolean enable, File saveFolder) {
		AtchFileVO atchFile = mapper.selectAtchFile(atchFileId, enable);
		Optional.ofNullable(atchFile)
				.map(AtchFileVO::getFileDetails)
				.ifPresent(fds -> 
					fds.forEach(fd -> mergeMetadAndBinaryData(fd, saveFolder))
				);
		return atchFile;
	}

	@Override
	public AtchFileDetailVO readAtchFileDetail(int atchFileId, int fileSn, File saveFolder) {
		AtchFileDetailVO fileDetail = mapper.selectAtchFileDetail(atchFileId, fileSn);
		if (fileDetail != null) {
			mergeMetadAndBinaryData(fileDetail, saveFolder);
			mapper.incrementDowncount(atchFileId, fileSn);
		}
		return fileDetail;
	}

	/**
	 * 파일 한건의 메타데이터와 2진 데이터 삭제
	 * 
	 * @param fileDetail
	 * @param saveFolder
	 * @throws IOException
	 */
	private void deleteFileDetail(AtchFileDetailVO fileDetail, File saveFolder) throws IOException {
		mergeMetadAndBinaryData(fileDetail, saveFolder);
		FileUtils.deleteQuietly(fileDetail.getSavedFile().getFile());
		mapper.deleteAtchFileDetail(fileDetail.getAtchFileId(), fileDetail.getFileSn());
	}

	@Override
	public void removeAtchFileDetail(int atchFileId, int fileSn, File saveFolder) {
		AtchFileDetailVO target = mapper.selectAtchFileDetail(atchFileId, fileSn);
		Optional.ofNullable(target)
				.ifPresent(
					Failable.asConsumer(fd -> 
						deleteFileDetail(fd, saveFolder)
					)
				);
	}

	@Override
	public void disableAtchFile(int atchFildId) {
		mapper.disableAtchFile(atchFildId);
	}

	@Override
	public void removeDiabledAtchFile(int atchFileId) {
		mapper.deleteDisabledAtchFileDetails(atchFileId);
		mapper.deleteDisabledAtchFile(atchFileId);
	}

}
