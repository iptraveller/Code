function drawPie(url,text,elementId) {
    $.ajax({
        "type":"post",
        "async":false,
        "url":url,
        "dataType":"json",
        "success":function(response) {
            var xData = []
            var count = []
            response.forEach(function(value) {
                xData.push(value.name)
                count.push(value)
            });

            var option = {
                title : {
                    text: text,
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient : 'vertical',
                    x : 'right',
                    data:xData
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        magicType : {
                            show: true,
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                }
                            }
                        }
                    }
                },
                calculable : true,
                series : [
                    {
                        name:text,
                        type:'pie',
                        radius : '45%',
                        center: ['50%', '60%'],
                        data:count
                    }
                ]
            };


            //初始化echarts实例
            var myChart = echarts.init(document.getElementById(elementId));
            //使用制定的配置项和数据显示图表
            myChart.setOption(option);
        }
    });
}