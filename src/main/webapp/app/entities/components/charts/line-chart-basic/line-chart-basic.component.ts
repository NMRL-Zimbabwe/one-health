import { Component, OnInit } from '@angular/core';
import * as echarts from 'echarts';

@Component({
  selector: 'jhi-line-chart-basic',
  templateUrl: './line-chart-basic.component.html',
  styleUrls: ['./line-chart-basic.component.scss'],
})
export class LineChartBasicComponent implements OnInit {
  constructor() {
    //
  }

  ngOnInit(): void {
    this.plot();
  }

  plot(): void {
    const chartDom = document.getElementById('line-chart')!;
    const myChart = echarts.init(chartDom);
    let option;

    option = {
      xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      },
      yAxis: {
        type: 'value',
      },
      series: [
        {
          data: [150, 230, 224, 218, 135, 147, 260],
          type: 'line',
        },
      ],
    };

    option && myChart.setOption(option);
  }
}
