import { Component, OnInit, Input } from '@angular/core';
import * as echarts from 'echarts';

@Component({
  selector: 'jhi-bar-label-rotation',
  templateUrl: './bar-label-rotation.component.html',
  styleUrls: ['./bar-label-rotation.component.scss'],
})
export class BarLabelRotationComponent implements OnInit {
  @Input() data: string | undefined;

  constructor() {
    //
  }

  ngOnInit(): void {
    this.plot();
  }

  plot(): void {
    const app: any = {};
    type EChartsOption = echarts.EChartsOption;

    const chartDom = document.getElementById('bar-chart')!;
    const myChart = echarts.init(chartDom);
    let option: EChartsOption;

    const posList = [
      'left',
      'right',
      'top',
      'bottom',
      'inside',
      'insideTop',
      'insideLeft',
      'insideRight',
      'insideBottom',
      'insideTopLeft',
      'insideTopRight',
      'insideBottomLeft',
      'insideBottomRight',
    ] as const;

    app.configParameters = {
      rotate: {
        min: -90,
        max: 90,
      },
      align: {
        options: {
          left: 'left',
          center: 'center',
          right: 'right',
        },
      },
      verticalAlign: {
        options: {
          top: 'top',
          middle: 'middle',
          bottom: 'bottom',
        },
      },
      position: {
        options: posList.reduce(function (map, pos) {
          map[pos] = pos;
          return map;
        }, {} as Record<string, string>),
      },
      distance: {
        min: 0,
        max: 100,
      },
    };

    app.config = {
      rotate: 90,
      align: 'left',
      verticalAlign: 'middle',
      position: 'insideBottom',
      distance: 15,
      onChange() {
        const labelOption: BarLabelOption = {
          rotate: app.config.rotate,
          align: app.config.align,
          verticalAlign: app.config.verticalAlign,
          position: app.config.position,
          distance: app.config.distance,
        };
        myChart.setOption<echarts.EChartsOption>({
          series: [
            {
              label: labelOption,
            },
            {
              label: labelOption,
            },
            {
              label: labelOption,
            },
            {
              label: labelOption,
            },
          ],
        });
      },
    };

    type BarLabelOption = NonNullable<echarts.BarSeriesOption['label']>;

    const labelOption: BarLabelOption = {
      show: true,
      position: app.config.position,
      distance: app.config.distance,
      align: app.config.align,
      verticalAlign: app.config.verticalAlign,
      rotate: app.config.rotate,
      formatter: '{c}  {name|{a}}',
      fontSize: 16,
      rich: {
        name: {},
      },
    };

    option = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow',
        },
      },
      legend: {
        data: ['Human', 'Animal', 'EMA', 'Food'],
      },
      toolbox: {
        show: true,
        orient: 'vertical',
        left: 'right',
        top: 'center',
        feature: {
          mark: { show: true },
          dataView: { show: true, readOnly: false },
          magicType: { show: true, type: ['line', 'bar', 'stack'] },
          restore: { show: true },
          saveAsImage: { show: true },
        },
      },
      xAxis: [
        {
          type: 'category',
          axisTick: { show: false },
          data: ['Escherichia-Coli', 'Samlmonella', 'Campylobacter', 'Klebsiella', 'HelicoBacter'],
        },
      ],
      yAxis: [
        {
          type: 'value',
        },
      ],
      series: [
        {
          name: 'Human',
          type: 'bar',
          barGap: 0,
          label: labelOption,
          emphasis: {
            focus: 'series',
          },
          data: [100, 332, 301, 334, 390],
        },
        {
          name: 'Animal',
          type: 'bar',
          label: labelOption,
          emphasis: {
            focus: 'series',
          },
          data: [220, 182, 191, 234, 290],
        },
        {
          name: 'EMA',
          type: 'bar',
          label: labelOption,
          emphasis: {
            focus: 'series',
          },
          data: [150, 232, 201, 154, 190],
        },
        {
          name: 'Food',
          type: 'bar',
          label: labelOption,
          emphasis: {
            focus: 'series',
          },
          data: [98, 77, 101, 99, 40],
        },
      ],
    };

    option && myChart.setOption(option);
  }
}
