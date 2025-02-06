/**
 * 
 */
function handleUserTypeChange(event) {
  const selectedUserType = event.target.value;

  if (selectedUserType === 'professor') {
    // professorInsert.jsp로 이동
    window.location.href = '../person/newpro';
  } else {
    
  }
}