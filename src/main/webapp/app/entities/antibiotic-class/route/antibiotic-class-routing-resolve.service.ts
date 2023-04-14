import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAntibioticClass } from '../antibiotic-class.model';
import { AntibioticClassService } from '../service/antibiotic-class.service';

@Injectable({ providedIn: 'root' })
export class AntibioticClassRoutingResolveService implements Resolve<IAntibioticClass | null> {
  constructor(protected service: AntibioticClassService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAntibioticClass | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((antibioticClass: HttpResponse<IAntibioticClass>) => {
          if (antibioticClass.body) {
            return of(antibioticClass.body);
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
