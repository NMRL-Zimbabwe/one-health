import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemography } from '../demography.model';
import { DemographyService } from '../service/demography.service';

@Injectable({ providedIn: 'root' })
export class DemographyRoutingResolveService implements Resolve<IDemography | null> {
  constructor(protected service: DemographyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemography | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demography: HttpResponse<IDemography>) => {
          if (demography.body) {
            return of(demography.body);
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
