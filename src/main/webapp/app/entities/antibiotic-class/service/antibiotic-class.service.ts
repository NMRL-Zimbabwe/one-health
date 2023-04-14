import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAntibioticClass, NewAntibioticClass } from '../antibiotic-class.model';

export type PartialUpdateAntibioticClass = Partial<IAntibioticClass> & Pick<IAntibioticClass, 'id'>;

export type EntityResponseType = HttpResponse<IAntibioticClass>;
export type EntityArrayResponseType = HttpResponse<IAntibioticClass[]>;

@Injectable({ providedIn: 'root' })
export class AntibioticClassService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/antibiotic-classes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(antibioticClass: NewAntibioticClass): Observable<EntityResponseType> {
    return this.http.post<IAntibioticClass>(this.resourceUrl, antibioticClass, { observe: 'response' });
  }

  update(antibioticClass: IAntibioticClass): Observable<EntityResponseType> {
    return this.http.put<IAntibioticClass>(`${this.resourceUrl}/${this.getAntibioticClassIdentifier(antibioticClass)}`, antibioticClass, {
      observe: 'response',
    });
  }

  partialUpdate(antibioticClass: PartialUpdateAntibioticClass): Observable<EntityResponseType> {
    return this.http.patch<IAntibioticClass>(`${this.resourceUrl}/${this.getAntibioticClassIdentifier(antibioticClass)}`, antibioticClass, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAntibioticClass>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAntibioticClass[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAntibioticClassIdentifier(antibioticClass: Pick<IAntibioticClass, 'id'>): number {
    return antibioticClass.id;
  }

  compareAntibioticClass(o1: Pick<IAntibioticClass, 'id'> | null, o2: Pick<IAntibioticClass, 'id'> | null): boolean {
    return o1 && o2 ? this.getAntibioticClassIdentifier(o1) === this.getAntibioticClassIdentifier(o2) : o1 === o2;
  }

  addAntibioticClassToCollectionIfMissing<Type extends Pick<IAntibioticClass, 'id'>>(
    antibioticClassCollection: Type[],
    ...antibioticClassesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const antibioticClasses: Type[] = antibioticClassesToCheck.filter(isPresent);
    if (antibioticClasses.length > 0) {
      const antibioticClassCollectionIdentifiers = antibioticClassCollection.map(
        antibioticClassItem => this.getAntibioticClassIdentifier(antibioticClassItem)!
      );
      const antibioticClassesToAdd = antibioticClasses.filter(antibioticClassItem => {
        const antibioticClassIdentifier = this.getAntibioticClassIdentifier(antibioticClassItem);
        if (antibioticClassCollectionIdentifiers.includes(antibioticClassIdentifier)) {
          return false;
        }
        antibioticClassCollectionIdentifiers.push(antibioticClassIdentifier);
        return true;
      });
      return [...antibioticClassesToAdd, ...antibioticClassCollection];
    }
    return antibioticClassCollection;
  }
}
