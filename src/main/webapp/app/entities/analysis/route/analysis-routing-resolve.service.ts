import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnalysis } from '../analysis.model';
import { AnalysisService } from '../service/analysis.service';

@Injectable({ providedIn: 'root' })
export class AnalysisRoutingResolveService implements Resolve<IAnalysis | null> {
  constructor(protected service: AnalysisService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnalysis | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((analysis: HttpResponse<IAnalysis>) => {
          if (analysis.body) {
            return of(analysis.body);
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
