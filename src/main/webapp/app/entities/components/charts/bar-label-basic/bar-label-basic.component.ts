import { Component, OnInit } from '@angular/core';
import * as echarts from 'echarts';

@Component({
  selector: 'jhi-bar-label-basic',
  templateUrl: './bar-label-basic.component.html',
  styleUrls: ['./bar-label-basic.component.scss'],
})
export class BarLabelBasicComponent implements OnInit {
  constructor() {
    //
  }

  ngOnInit(): void {
    this.plot();
  }

  plot(): void {
    const chartDom = document.getElementById('jhi-bar')!;
    const myChart = echarts.init(chartDom);
    let option;

    option = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow',
        },
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true,
      },
      xAxis: [
        {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
          axisTick: {
            alignWithLabel: true,
          },
        },
      ],
      yAxis: [
        {
          type: 'value',
        },
      ],
      series: [
        {
          name: 'Direct',
          type: 'bar',
          barWidth: '60%',
          data: [10, 52, 200, 334, 390, 330, 220],
        },
      ],
    };

    option && myChart.setOption(option);
  }
}
