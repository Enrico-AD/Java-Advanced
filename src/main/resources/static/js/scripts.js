$("input[type=date]").datepicker({
  dateFormat: 'dd/mm/yyyy-',
  onSelect: function(dateText, inst) {
    $(inst).val(dateText); // Write the value in the input
  }
});