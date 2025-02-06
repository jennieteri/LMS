/**
 * 
 */
document.addEventListener("DOMContentLoaded", function () {
    const isFreshman = document.getElementById('isFreshman').value === 'true';

    if (isFreshman) {
        window.addEventListener('beforeunload', (e) => {
            e.preventDefault();
            e.returnValue = '수정을 완료해야 다른 페이지로 이동할 수 있습니다.';
        });

        document.querySelectorAll('.sidebar-link').forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                alert('수정을 완료해야 다른 페이지로 이동할 수 있습니다.');
            });
        });
    }
});
