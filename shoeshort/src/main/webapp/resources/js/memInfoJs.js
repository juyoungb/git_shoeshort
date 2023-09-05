function delAddr(maidx) {
  if (confirm('정말로 삭제하시겠습니까?')) {
    $.ajax({
      type: "POST",
      url: "addrDel",
      data: { "n": maidx },
      success: function (rs) {
    	 alert(rs);
        if (rs == 1) {
          alert('삭제되었습니다.');//안먹음 이유 모름
          location.reload();
        } else {  
          alert('삭제에 실패했습니다.');//안먹음 이유 모름
        }
      },
     error: function () {
    	 location.reload();
    	 alert('삭제되었습니다.');
      }
    });
  }
}
$(document).ready(function() {
	  $('#frm').submit(function(e) {
	    if ($('#chkVal').is(':checked')) {
	      $('#chkVal').val('y');
	    } else {
	      $('#chkVal').prop('checked', true);
	      $('#chkVal').val('n');
	    }
	  });
	});
