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
                color: [
                    '#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#40e0d0',
                    '#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#ffd700',
                    '#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0',
                    '#ff7f50', '#87cefa', '#da70d6', '#32cd32', '#6495ed'
                ],
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