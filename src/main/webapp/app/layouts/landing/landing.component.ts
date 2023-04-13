import { Component, OnInit } from '@angular/core';
import * as echarts from 'echarts';

@Component({
  selector: 'jhi-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss'],
})
export class LandingComponent implements OnInit {
  constructor() {
    //
  }

  ngOnInit(): void {
    this.titler();
    this.randomChart();
  }

  randomChart(): void {
    const chartDom = document.getElementById('random-chart')!;
    const myChart = echarts.init(chartDom);
    setInterval(() => {
      const options = this.getRandomChartOptions();
      myChart.setOption(options);
    }, 2000);
  }

  getRandomChartOptions(): any {
    const donut = {
      series: [
        {
          name: 'Area Mode',
          type: 'pie',
          radius: [20, 100],
          center: ['75%', '50%'],
          roseType: 'area',
          itemStyle: {
            borderRadius: 5,
          },
          data: [
            { value: Math.random(), name: 'EMA' },
            { value: Math.random(), name: 'Human' },
            { value: Math.random(), name: 'Aminal' },
            { value: Math.random(), name: 'Food' },
          ],
        },
      ],
    };

    const loading = {
      graphic: {
        elements: [
          {
            type: 'group',
            left: 'center',
            top: 'center',
            children: new Array(7).fill(0).map((val, i) => ({
              type: 'rect',
              x: i * 20,
              shape: {
                x: 0,
                y: -40,
                width: 10,
                height: 80,
              },
              style: {
                fill: '#5470c6',
              },
              keyframeAnimation: {
                duration: 1000,
                delay: i * 200,
                loop: true,
                keyframes: [
                  {
                    percent: 0.5,
                    scaleY: 0.3,
                    easing: 'cubicIn',
                  },
                  {
                    percent: 1,
                    scaleY: 1,
                    easing: 'cubicOut',
                  },
                ],
              },
            })),
          },
        ],
      },
    };
    const options = [donut, loading];
    return options[Math.floor(Math.random() * options.length)];
  }

  titler(): void {
    const chartDom = document.getElementById('titler')!;
    const myChart = echarts.init(chartDom);
    const option = {
      graphic: {
        elements: [
          {
            type: 'text',
            left: 'center',
            top: 'center',
            style: {
              text: 'One Health Dashboard',
              fontSize: 60,
              fontWeight: 'bold',
              lineDash: [0, 100],
              lineDashOffset: 0,
              fill: 'transparent',
              stroke: '#000',
              lineWidth: 1,
            },
            keyframeAnimation: {
              duration: 3000,
              loop: false,
              keyframes: [
                {
                  percent: 0.7,
                  style: {
                    fill: 'transparent',
                    lineDashOffset: 100,
                    lineDash: [100, 0],
                  },
                },
                {
                  // Stop for a while.
                  percent: 0.1,
                  style: {
                    fill: 'transparent',
                  },
                },
                {
                  percent: 1,
                  style: {
                    fill: 'black',
                  },
                },
              ],
            },
          },
        ],
      },
    };

    myChart.setOption(option);
  }
}
