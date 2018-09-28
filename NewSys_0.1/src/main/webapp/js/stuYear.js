function createStuYear() {
	var recentlyStuYears = new Array();
	var now = new Date();
	var nowYear = now.getFullYear() ;
	var nowMonth = now.getMonth() + 1;
	var nowDay = now.getDate();
	var j = 0;
	for (var i = nowYear - 5; i <= nowYear + 5; i++) {
		recentlyStuYears[j] = (i - 1) + "-" + i + "-1";
		j++;
		recentlyStuYears[j] = (i - 1) + "-" + i + "-2";
		j++;
	}
	return recentlyStuYears;
}