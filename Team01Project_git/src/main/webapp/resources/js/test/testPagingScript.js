/**
 * 
 */
document.addEventListener("DOMContentLoaded", () => {
    let $searchArea = $("[data-pg-target][data-pg-fn-name]").filter((index, el) => {
        let targetSelector = el.dataset.pgTarget;
        let $searchForm = $(targetSelector);
        let filtered = $searchForm.length === 1;
        if (filtered) {
            let fnName = el.dataset.pgFnName;
            window[fnName] = function (page) {
                $searchForm.find("input[name=page]").val(page);
                $searchForm.submit();
            };
        }
        return filtered;
    });

    $searchArea.find("#search-btn").on("click", function () {
        let $parent = $(this).parents(".search-area:first");
        let targetSelector = $parent?.data("pgTarget");
        let $searchForm = $(targetSelector);

        // 선택된 라디오 버튼의 값을 가져옵니다.
        let selectedSearchType = $parent.find("input[name='searchType']:checked").val();
        
        // 선택된 값을 hidden form의 searchType 입력 필드에 설정합니다.
        $searchForm.find("input[name='searchType']").val(selectedSearchType);

        $searchForm.submit();
    });

    // 페이지 로드 시 URL 파라미터에 따라 라디오 버튼 선택
    let urlParams = new URLSearchParams(window.location.search);
    let searchType = urlParams.get('searchType');
    if (searchType) {
        $(`input[name='searchType'][value='${searchType}']`).prop('checked', true);
    }
});
