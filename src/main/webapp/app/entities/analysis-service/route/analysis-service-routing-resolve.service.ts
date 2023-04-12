import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnalysisService } from '../analysis-service.model';
import { AnalysisServiceService } from '../service/analysis-service.service';

@Injectable({ providedIn: 'root' })
export class AnalysisServiceRoutingResolveService implements Resolve<IAnalysisService | null> {
  constructor(protected service: AnalysisServiceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnalysisService | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((analysisService: HttpResponse<IAnalysisService>) => {
          if (analysisService.body) {
            return of(analysisService.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
