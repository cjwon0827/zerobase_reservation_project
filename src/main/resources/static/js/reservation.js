let dateElement = document.getElementById('date');
        let date = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, 16);
        dateElement.value = date;
        dateElement.setAttribute("min", date);

        function setMinValue() {
            if(dateElement.value < date) {
                alert('현재 시간보다 이전의 날짜는 설정할 수 없습니다.');
                dateElement.value = date;
            }
            }