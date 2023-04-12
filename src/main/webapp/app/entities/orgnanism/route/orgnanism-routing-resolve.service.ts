import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrgnanism } from '../orgnanism.model';
import { OrgnanismService } from '../service/orgnanism.service';

@Injectable({ providedIn: 'root' })
export class OrgnanismRoutingResolveService implements Resolve<IOrgnanism | null> {
  constructor(protected service: OrgnanismService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrgnanism | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((orgnanism: HttpResponse<IOrgnanism>) => {
          if (orgnanism.body) {
            return of(orgnanism.body);
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
