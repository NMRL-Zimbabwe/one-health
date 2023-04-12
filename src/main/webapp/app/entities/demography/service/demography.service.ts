import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemography, NewDemography } from '../demography.model';

export type PartialUpdateDemography = Partial<IDemography> & Pick<IDemography, 'id'>;

export type EntityResponseType = HttpResponse<IDemography>;
export type EntityArrayResponseType = HttpResponse<IDemography[]>;

@Injectable({ providedIn: 'root' })
export class DemographyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demographies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demography: NewDemography): Observable<EntityResponseType> {
    return this.http.post<IDemography>(this.resourceUrl, demography, { observe: 'response' });
  }

  update(demography: IDemography): Observable<EntityResponseType> {
    return this.http.put<IDemography>(`${this.resourceUrl}/${this.getDemographyIdentifier(demography)}`, demography, {
      observe: 'response',
    });
  }

  partialUpdate(demography: PartialUpdateDemography): Observable<EntityResponseType> {
    return this.http.patch<IDemography>(`${this.resourceUrl}/${this.getDemographyIdentifier(demography)}`, demography, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDemography>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDemography[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDemographyIdentifier(demography: Pick<IDemography, 'id'>): number {
    return demography.id;
  }

  compareDemography(o1: Pick<IDemography, 'id'> | null, o2: Pick<IDemography, 'id'> | null): boolean {
    return o1 && o2 ? this.getDemographyIdentifier(o1) === this.getDemographyIdentifier(o2) : o1 === o2;
  }

  addDemographyToCollectionIfMissing<Type extends Pick<IDemography, 'id'>>(
    demographyCollection: Type[],
    ...demographiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const demographies: Type[] = demographiesToCheck.filter(isPresent);
    if (demographies.length > 0) {
      const demographyCollectionIdentifiers = demographyCollection.map(demographyItem => this.getDemographyIdentifier(demographyItem)!);
      const demographiesToAdd = demographies.filter(demographyItem => {
        const demographyIdentifier = this.getDemographyIdentifier(demographyItem);
        if (demographyCollectionIdentifiers.includes(demographyIdentifier)) {
          return false;
        }
        demographyCollectionIdentifiers.push(demographyIdentifier);
        return true;
      });
      return [...demographiesToAdd, ...demographyCollection];
    }
    return demographyCollection;
  }
}
