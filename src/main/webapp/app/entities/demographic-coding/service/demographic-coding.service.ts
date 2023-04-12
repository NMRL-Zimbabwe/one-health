import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemographicCoding, NewDemographicCoding } from '../demographic-coding.model';

export type PartialUpdateDemographicCoding = Partial<IDemographicCoding> & Pick<IDemographicCoding, 'id'>;

export type EntityResponseType = HttpResponse<IDemographicCoding>;
export type EntityArrayResponseType = HttpResponse<IDemographicCoding[]>;

@Injectable({ providedIn: 'root' })
export class DemographicCodingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demographic-codings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demographicCoding: NewDemographicCoding): Observable<EntityResponseType> {
    return this.http.post<IDemographicCoding>(this.resourceUrl, demographicCoding, { observe: 'response' });
  }

  update(demographicCoding: IDemographicCoding): Observable<EntityResponseType> {
    return this.http.put<IDemographicCoding>(
      `${this.resourceUrl}/${this.getDemographicCodingIdentifier(demographicCoding)}`,
      demographicCoding,
      { observe: 'response' }
    );
  }

  partialUpdate(demographicCoding: PartialUpdateDemographicCoding): Observable<EntityResponseType> {
    return this.http.patch<IDemographicCoding>(
      `${this.resourceUrl}/${this.getDemographicCodingIdentifier(demographicCoding)}`,
      demographicCoding,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDemographicCoding>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDemographicCoding[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDemographicCodingIdentifier(demographicCoding: Pick<IDemographicCoding, 'id'>): number {
    return demographicCoding.id;
  }

  compareDemographicCoding(o1: Pick<IDemographicCoding, 'id'> | null, o2: Pick<IDemographicCoding, 'id'> | null): boolean {
    return o1 && o2 ? this.getDemographicCodingIdentifier(o1) === this.getDemographicCodingIdentifier(o2) : o1 === o2;
  }

  addDemographicCodingToCollectionIfMissing<Type extends Pick<IDemographicCoding, 'id'>>(
    demographicCodingCollection: Type[],
    ...demographicCodingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const demographicCodings: Type[] = demographicCodingsToCheck.filter(isPresent);
    if (demographicCodings.length > 0) {
      const demographicCodingCollectionIdentifiers = demographicCodingCollection.map(
        demographicCodingItem => this.getDemographicCodingIdentifier(demographicCodingItem)!
      );
      const demographicCodingsToAdd = demographicCodings.filter(demographicCodingItem => {
        const demographicCodingIdentifier = this.getDemographicCodingIdentifier(demographicCodingItem);
        if (demographicCodingCollectionIdentifiers.includes(demographicCodingIdentifier)) {
          return false;
        }
        demographicCodingCollectionIdentifiers.push(demographicCodingIdentifier);
        return true;
      });
      return [...demographicCodingsToAdd, ...demographicCodingCollection];
    }
    return demographicCodingCollection;
  }
}
