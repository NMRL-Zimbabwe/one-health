import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnalysis } from '../analysis.model';

@Component({
  selector: 'jhi-analysis-detail',
  templateUrl: './analysis-detail.component.html',
})
export class AnalysisDetailComponent implements OnInit {
  analysis: IAnalysis | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analysis }) => {
      this.analysis = analysis;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
