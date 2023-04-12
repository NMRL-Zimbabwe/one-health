import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemographicCoding } from '../demographic-coding.model';
import { DemographicCodingService } from '../service/demographic-coding.service';

@Injectable({ providedIn: 'root' })
export class DemographicCodingRoutingResolveService implements Resolve<IDemographicCoding | null> {
  constructor(protected service: DemographicCodingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemographicCoding | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demographicCoding: HttpResponse<IDemographicCoding>) => {
          if (demographicCoding.body) {
            return of(demographicCoding.body);
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
