$(function () {

    /*时间弹框*/
    try {

        $.dateRangePickerLanguages['custom'] = {
            'selected': 'Choosed:',
            'days': 'Days',
            'apply': 'Close',
            'week-1': 'Mon',
            'week-2': 'Tue',
            'week-3': 'Wed',
            'week-4': 'Thu',
            'week-5': 'Fri',
            'week-6': 'Sat',
            'week-7': 'Sun',
            'month-name': ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            'shortcuts': 'Shortcuts',
            'past': 'Past',
            '7days': '7days',
            '14days': '14days',
            '30days': '30days',
            'previous': 'Previous',
            'prev-week': 'Week',
            'prev-month': 'Month',
            'prev-quarter': 'Quarter',
            'prev-year': 'Year',
            'less-than': 'Date range should longer than %d days',
            'more-than': 'Date range should less than %d days',
            'default-more': 'Please select a date range longer than %d days',
            'default-less': 'Please select a date range less than %d days',
            'default-range': 'Please select a date range between %d and %d days',
            'default-default': 'This is costom language'
        };
    } catch (e) {
      //  window.top.warmDivDialog("e111", e,null,null)
    }

});

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
}