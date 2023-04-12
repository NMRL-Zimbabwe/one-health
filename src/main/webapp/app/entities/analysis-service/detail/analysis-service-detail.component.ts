import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnalysisService } from '../analysis-service.model';

@Component({
  selector: 'jhi-analysis-service-detail',
  templateUrl: './analysis-service-detail.component.html',
})
export class AnalysisServiceDetailComponent implements OnInit {
  analysisService: IAnalysisService | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analysisService }) => {
      this.analysisService = analysisService;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
