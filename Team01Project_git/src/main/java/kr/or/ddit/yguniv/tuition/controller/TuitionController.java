package kr.or.ddit.yguniv.tuition.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import kr.or.ddit.yguniv.commons.service.CommonCodeServiceImpl;
import kr.or.ddit.yguniv.paging.DataTablesPaging;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.tuition.service.TuitionServiceImpl;
import kr.or.ddit.yguniv.vo.AwardVO;
import kr.or.ddit.yguniv.vo.TuitionVO;

@Controller
@RequestMapping("/tuition")
public class TuitionController {
	@Autowired
	private TuitionServiceImpl tuiService;
	@Autowired
	private CommonCodeServiceImpl cocoService;
	
	@GetMapping
	public String tuitionPage(Model model) {
		model.addAttribute("condition", new TuitionVO());
		model.addAttribute("tuitionStatusList", cocoService.getCodeList("TUIT"));
		model.addAttribute("semesterList", cocoService.getSemesterList(null));
		return "tuition/tuitionList";
	}
	
	@PostMapping
	@ResponseBody
	public Map<String, Object> selectTuitionList(@RequestBody DataTablesPaging<TuitionVO> paging){
		Map<String, Object> result = new HashMap<>();
	    result.put("draw", paging.getDraw());
	    result.put("data", tuiService.selectTuitionList(paging));
	    result.put("recordsTotal", paging.getRecordsTotal());
	    result.put("recordsFiltered", paging.getRecordsFiltered());
	    result.put("pageHTML", new BootStrapPaginationRenderer().renderPagination(paging.getPaginationInfo(), "fnPaging"));
		return result;
	}
	
	@PutMapping("{semstrNo}/{stuId}")
	public void updateTuition(TuitionVO tuitionVO) {
		tuiService.updateTuition(tuitionVO);
	}
	
	@GetMapping("{semstrNo}/{stuId}/invoice")
	public void selectTuitionInvoicePDF(TuitionVO tuitionVO, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		TuitionVO res = tuiService.selectTuition(tuitionVO);
		
		resp.setContentType("application/pdf");
		String fileName = UriUtils.encode(String.format("%s등록금고지서(%s)", res.getSemstrNo(), res.getStudentVO().getNm()), StandardCharsets.UTF_8);
	    resp.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
		Document doc = new Document(PageSize.A4.rotate(), 30, 30, 40, 40);
		ServletOutputStream os = resp.getOutputStream();
		try{
			PdfWriter writer = PdfWriter.getInstance(doc, os);
			doc.addTitle("등록금고지서");
			doc.open();
			// 글꼴 설정 (기본 폰트)
			BaseFont baseFont = BaseFont.createFont("HYGoThic-Medium", "UniKS-UCS2-H", BaseFont.EMBEDDED);
			Font font = new Font(baseFont, 10);
			Font headerFont = new Font(baseFont, 15, Font.BOLD);
			Font signatureFont = new Font(baseFont, 20, Font.BOLD);
			
			PdfContentByte canvas = writer.getDirectContent();
			// 가운데 점선
			canvas.setLineDash(3f, 3f);
			float x = doc.getPageSize().getWidth() / 2;  // 페이지 중앙 x 좌표
			float y1 = doc.top();
			float y2 = doc.bottom();
			canvas.moveTo(x, y1);
			canvas.lineTo(x, y2);
			canvas.stroke();
			
			// 실선 복구
			canvas.setLineDash(0f);
			
			// 학교 마크
			String ygunivPath = req.getServletContext().getRealPath("/resources/prodImages/yguniv.png");
			Image yguniv = Image.getInstance(ygunivPath);
			String signaturePath = req.getServletContext().getRealPath("/resources/prodImages/yguniv_signature.png");
			Image signature = Image.getInstance(signaturePath);
			
			
			// 전체 레이아웃 테이블 (3열로 분리)
			PdfPTable mainTable = new PdfPTable(3);
			mainTable.setWidthPercentage(100);
			mainTable.setWidths(new int[]{5, 1, 5}); // 테이블 공백 테이블
			
			// 은행용 & 학생용 각각의 테이블 생성
			PdfPTable table1 = createNoticeTable("은행용", font, headerFont, signatureFont, res, yguniv, signature);
			PdfPTable table2 = createNoticeTable("학생용", font, headerFont, signatureFont, res, yguniv, signature);
			PdfPCell cell2 = new PdfPCell();
			cell2.setBorderWidthTop(0f);
			cell2.setBorderWidthBottom(0f); 
			
			// 테이블 추가
			mainTable.addCell(table1);
			mainTable.addCell(cell2);
			mainTable.addCell(table2);
			
			doc.add(mainTable);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			doc.close();
		}
	}
	
	private static PdfPTable createNoticeTable(String usageType, Font font, Font headerFont, Font signatureFont, TuitionVO vo, Image yguniv, Image signature) throws DocumentException, MalformedURLException, IOException {
        PdfPTable table = new PdfPTable(1); // 단일 열
        table.setWidthPercentage(100);
        
        // 제목
        PdfPCell titleCell = new PdfPCell(new Phrase(String.format("%s년 %s학기 등록금고지서\n(%s)", vo.getSemstrNo().substring(0, 4), vo.getSemstrNo().substring(4, 6), usageType), headerFont));
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setPadding(30);
        titleCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(titleCell);

        // 기본 정보 테이블
        PdfPTable infoTable = new PdfPTable(4);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new int[]{1, 2, 1, 2});
        
        addCell(infoTable, "학과", font, Element.ALIGN_CENTER, true, 1);
        addCell(infoTable, vo.getStudentVO().getDepartmentVO().getDeptNm(), font, Element.ALIGN_CENTER, false, 1);
        addCell(infoTable, "학년", font, Element.ALIGN_CENTER, true, 1);
        addCell(infoTable, vo.getStudentVO().getGradeCocoVO().getCocoStts(), font, Element.ALIGN_CENTER, false, 1);

        addCell(infoTable, "학번", font, Element.ALIGN_CENTER, true, 1);
        addCell(infoTable, vo.getStuId(), font, Element.ALIGN_CENTER, false, 1);
        addCell(infoTable, "성명", font, Element.ALIGN_CENTER, true, 1);
        addCell(infoTable, vo.getStudentVO().getNm(), font, Element.ALIGN_CENTER, false, 1);

        PdfPCell infoCell = new PdfPCell(infoTable);
        infoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        infoCell.setPadding(10);
        infoCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(infoCell);

        // 등록금 내역 테이블
        DecimalFormat formatter = new DecimalFormat("#,###");
        long total = vo.getTuitTuition() - Optional.ofNullable(vo.getAwardVO()).map(AwardVO::getAwardGiveAmt).orElse(0);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        PdfPTable feeTable = new PdfPTable(2);
        feeTable.setWidthPercentage(100);
        feeTable.setWidths(new int[]{1, 2});

        addCell(feeTable, "등록금 내역", font, Element.ALIGN_CENTER, true, 2);
        
        addCell(feeTable, "등록금액", font, Element.ALIGN_CENTER, true, 1);
        addCell(feeTable, String.format("%s 원", formatter.format(vo.getTuitTuition())), font, Element.ALIGN_RIGHT, false, 1);
        
        addCell(feeTable, "장학금액", font, Element.ALIGN_CENTER, true, 1);
        addCell(feeTable, Optional.ofNullable(vo.getAwardVO()).map(AwardVO::getAwardGiveAmt).map(a->String.format("%s 원", formatter.format(a))).orElse("0 원"), font, Element.ALIGN_RIGHT, false, 1);

        addCell(feeTable, "납입금액", font, Element.ALIGN_CENTER, true, 1);
        addCell(feeTable, String.format("%s 원", formatter.format(total)), font, Element.ALIGN_RIGHT, false, 3);
        
        PdfPCell feeCell = new PdfPCell(feeTable);
        feeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        feeCell.setPadding(10);
        feeCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(feeCell);
        
        PdfPTable dayTable = new PdfPTable(2);
        dayTable.setWidthPercentage(100);
        dayTable.setWidths(new int[]{1, 2});
        
        addCell(dayTable, "등록기간", font, Element.ALIGN_CENTER, true, 2);
        addCell(dayTable, "정규등록", font, Element.ALIGN_CENTER, true, 1);
        addCell(dayTable, String.format("%s ~ %s", vo.getRegularRegist().getNtcDt().format(dtf), vo.getRegularRegist().getNtcEt().format(dtf)), font, Element.ALIGN_CENTER, false, 1);
        addCell(dayTable, "추가등록", font, Element.ALIGN_CENTER, true, 1);
        addCell(dayTable, String.format("%s ~ %s", vo.getAdditionalRegist().getNtcDt().format(dtf), vo.getAdditionalRegist().getNtcEt().format(dtf)), font, Element.ALIGN_CENTER, false, 1);
        
        PdfPCell dayCell = new PdfPCell(dayTable);
        dayCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        dayCell.setPadding(10);
        dayCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(dayCell);

        // 하단 계좌 정보
        PdfPTable bankTable = new PdfPTable(2);
        bankTable.setWidthPercentage(100);
        bankTable.setWidths(new int[]{1, 1});
        
        addCell(bankTable, "연근은행 입금전용계좌", font, Element.ALIGN_CENTER, true, 1);
        addCell(bankTable, vo.getTuitVrActno(), font, Element.ALIGN_CENTER, false, 1);
        
        PdfPCell bankCell = new PdfPCell(bankTable);
        bankCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        bankCell.setPadding(10);
        bankCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(bankCell);

        // 총장 직인
        yguniv.scaleAbsolute(30, 30); // 가로, 세로 최대 크기 설정
        yguniv.setBorder(Rectangle.NO_BORDER);
        Chunk ygunivChunk = new Chunk(yguniv, -2, -7, true);
        Chunk textChunk = new Chunk("연근대학교 총장", signatureFont);
        signature.scaleAbsolute(40, 40); // 가로, 세로 최대 크기 설정
        signature.setBorder(Rectangle.NO_BORDER);
        Chunk signatureChunk = new Chunk(signature, -10, -10, true);
        
        Phrase signatureArea = new Phrase();
        signatureArea.add(ygunivChunk);
        signatureArea.add(textChunk);
        signatureArea.add(signatureChunk);
        
        PdfPCell signatureCell = new PdfPCell(signatureArea);
        signatureCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        signatureCell.setPadding(20);
        signatureCell.setPaddingBottom(50);
        signatureCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(signatureCell);
        
        return table;
    }

    /**
     * @param PdfPTable table : 셀을 추가할 테이블
     * @param String content : 셀 콘텐츠
     * @param Font font : 셀 폰트 설정
     * @param int alignment : 셀 정렬
     * @param boolean isHeader : true 시 뒷배경을 회색으로
     * @param int colspan : 셀병합 수
     */
    private static void addCell(
    	PdfPTable table
    	, String content
    	, Font font
    	, int alignment
    	, boolean isHeader
    	, int colspan
    ) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(colspan);
        if (isHeader) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        }
        cell.setPadding(7);
        table.addCell(cell);
    }
    
    @GetMapping("{semstrNo}/{stuId}/confirm")
    public void selectTuitionConfirmPDF(TuitionVO tuitionVO, HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	TuitionVO vo = tuiService.selectTuition(tuitionVO);
    	
    	resp.setContentType("application/pdf");
    	String fileName = UriUtils.encode(String.format("%s납부확인서(%s)", vo.getSemstrNo(), vo.getStudentVO().getNm()), StandardCharsets.UTF_8);
    	resp.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
    	Document doc = new Document(PageSize.A4, 30, 30, 40, 40);
    	ServletOutputStream os = resp.getOutputStream();
    	try{
    		PdfWriter writer = PdfWriter.getInstance(doc, os);
    		doc.addTitle("납부확인서");
    		doc.open();
    		// 글꼴 설정 (기본 폰트)
    		BaseFont baseFont = BaseFont.createFont("HYGoThic-Medium", "UniKS-UCS2-H", BaseFont.EMBEDDED);
    		Font font = new Font(baseFont, 15);
    		Font headerFont = new Font(baseFont, 20, Font.BOLD);
    		Font signatureFont = new Font(baseFont, 25, Font.BOLD);
    		
    		// 시그니쳐
    		String ygunivPath = req.getServletContext().getRealPath("/resources/prodImages/yguniv.png");
    		Image yguniv = Image.getInstance(ygunivPath);
    		String signaturePath = req.getServletContext().getRealPath("/resources/prodImages/yguniv_signature.png");
    		Image signature = Image.getInstance(signaturePath);
    		
    		PdfPTable table = new PdfPTable(1);
    		table.setWidthPercentage(100);
    		
    		// 제목
            PdfPCell titleCell = new PdfPCell(new Phrase(String.format("%s년 %s학기 등록금 납부 확인서", vo.getSemstrNo().substring(0, 4), vo.getSemstrNo().substring(4, 6)), headerFont));
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setPadding(20);
            titleCell.setPaddingTop(50);
            titleCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(titleCell);
    		
            // 기본 정보 테이블
            PdfPTable infoTable = new PdfPTable(4);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new int[]{1, 2, 1, 2});
            
            addCell(infoTable, "학과", font, Element.ALIGN_CENTER, true, 1);
            addCell(infoTable, vo.getStudentVO().getDepartmentVO().getDeptNm(), font, Element.ALIGN_CENTER, false, 1);
            addCell(infoTable, "학년", font, Element.ALIGN_CENTER, true, 1);
            addCell(infoTable, vo.getStudentVO().getGradeCocoVO().getCocoStts(), font, Element.ALIGN_CENTER, false, 1);

            addCell(infoTable, "학번", font, Element.ALIGN_CENTER, true, 1);
            addCell(infoTable, vo.getStuId(), font, Element.ALIGN_CENTER, false, 1);
            addCell(infoTable, "성명", font, Element.ALIGN_CENTER, true, 1);
            addCell(infoTable, vo.getStudentVO().getNm(), font, Element.ALIGN_CENTER, false, 1);

            PdfPCell infoCell = new PdfPCell(infoTable);
            infoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            infoCell.setPadding(20);
            infoCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(infoCell);
            
            // 등록금 내역 테이블
            DecimalFormat formatter = new DecimalFormat("#,###");
            long total = vo.getTuitTuition() - Optional.ofNullable(vo.getAwardVO()).map(AwardVO::getAwardGiveAmt).orElse(0);
            PdfPTable feeTable = new PdfPTable(2);
            feeTable.setWidthPercentage(100);
            feeTable.setWidths(new int[]{1, 2});

            addCell(feeTable, "납부확인일자", font, Element.ALIGN_CENTER, true, 1);
            addCell(feeTable, String.format("%s.%s.%s", vo.getTuitPayPeriod().substring(0, 4), vo.getTuitPayPeriod().substring(4, 6), vo.getTuitPayPeriod().substring(6)), font, Element.ALIGN_CENTER, false, 1);
            
            addCell(feeTable, "등록금액", font, Element.ALIGN_CENTER, true, 1);
            addCell(feeTable, String.format("%s 원", formatter.format(vo.getTuitTuition())), font, Element.ALIGN_RIGHT, false, 1);
            
            addCell(feeTable, "장학금액", font, Element.ALIGN_CENTER, true, 1);
            addCell(feeTable, Optional.ofNullable(vo.getAwardVO()).map(AwardVO::getAwardGiveAmt).map(a->String.format("%s 원", formatter.format(a))).orElse("0 원"), font, Element.ALIGN_RIGHT, false, 1);

            addCell(feeTable, "납부금액", font, Element.ALIGN_CENTER, true, 1);
            addCell(feeTable, String.format("%s 원", formatter.format(total)), font, Element.ALIGN_RIGHT, false, 1);
            
            PdfPCell feeCell = new PdfPCell(feeTable);
            feeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            feeCell.setPadding(20);
            feeCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(feeCell);
            
            PdfPCell contentCell = new PdfPCell(new Phrase("위와같이 등록금을 납부하였음을 확인합니다.", font));
            contentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            contentCell.setPadding(40);
            contentCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(contentCell);
            
            // 총장 직인
            yguniv.scaleAbsolute(35, 35); // 가로, 세로 최대 크기 설정
            yguniv.setBorder(Rectangle.NO_BORDER);
            Chunk ygunivChunk = new Chunk(yguniv, -2, -7, true);
            Chunk textChunk = new Chunk("연근대학교 총장", signatureFont);
            signature.scaleAbsolute(45, 45); // 가로, 세로 최대 크기 설정
            signature.setBorder(Rectangle.NO_BORDER);
            Chunk signatureChunk = new Chunk(signature, -10, -10, true);
            
            Phrase signatureArea = new Phrase();
            signatureArea.add(ygunivChunk);
            signatureArea.add(textChunk);
            signatureArea.add(signatureChunk);
            
            PdfPCell signatureCell = new PdfPCell(signatureArea);
            signatureCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            signatureCell.setPadding(20);
            signatureCell.setPaddingBottom(60);
            signatureCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(signatureCell);
            
    		doc.add(table);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		doc.close();
    	}
    }
}
