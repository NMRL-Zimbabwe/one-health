import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BarLabelRotationComponent } from './charts/bar-label-rotation/bar-label-rotation.component';
import { BarLabelBasicComponent } from './charts/bar-label-basic/bar-label-basic.component';
import { PieChartBasicComponent } from './charts/pie-chart-basic/pie-chart-basic.component';
import { LineChartBasicComponent } from './charts/line-chart-basic/line-chart-basic.component';

@NgModule({
  declarations: [BarLabelRotationComponent, BarLabelBasicComponent, PieChartBasicComponent, LineChartBasicComponent],
  imports: [CommonModule],
  exports: [BarLabelRotationComponent, BarLabelBasicComponent, PieChartBasicComponent, LineChartBasicComponent],
})
export class AmrChartsModule {}
