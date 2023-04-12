import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISampleType, NewSampleType } from '../sample-type.model';

export type PartialUpdateSampleType = Partial<ISampleType> & Pick<ISampleType, 'id'>;

export type EntityResponseType = HttpResponse<ISampleType>;
export type EntityArrayResponseType = HttpResponse<ISampleType[]>;

@Injectable({ providedIn: 'root' })
export class SampleTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sample-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sampleType: NewSampleType): Observable<EntityResponseType> {
    return this.http.post<ISampleType>(this.resourceUrl, sampleType, { observe: 'response' });
  }

  update(sampleType: ISampleType): Observable<EntityResponseType> {
    return this.http.put<ISampleType>(`${this.resourceUrl}/${this.getSampleTypeIdentifier(sampleType)}`, sampleType, {
      observe: 'response',
    });
  }

  partialUpdate(sampleType: PartialUpdateSampleType): Observable<EntityResponseType> {
    return this.http.patch<ISampleType>(`${this.resourceUrl}/${this.getSampleTypeIdentifier(sampleType)}`, sampleType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISampleType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISampleType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSampleTypeIdentifier(sampleType: Pick<ISampleType, 'id'>): number {
    return sampleType.id;
  }

  compareSampleType(o1: Pick<ISampleType, 'id'> | null, o2: Pick<ISampleType, 'id'> | null): boolean {
    return o1 && o2 ? this.getSampleTypeIdentifier(o1) === this.getSampleTypeIdentifier(o2) : o1 === o2;
  }

  addSampleTypeToCollectionIfMissing<Type extends Pick<ISampleType, 'id'>>(
    sampleTypeCollection: Type[],
    ...sampleTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sampleTypes: Type[] = sampleTypesToCheck.filter(isPresent);
    if (sampleTypes.length > 0) {
      const sampleTypeCollectionIdentifiers = sampleTypeCollection.map(sampleTypeItem => this.getSampleTypeIdentifier(sampleTypeItem)!);
      const sampleTypesToAdd = sampleTypes.filter(sampleTypeItem => {
        const sampleTypeIdentifier = this.getSampleTypeIdentifier(sampleTypeItem);
        if (sampleTypeCollectionIdentifiers.includes(sampleTypeIdentifier)) {
          return false;
        }
        sampleTypeCollectionIdentifiers.push(sampleTypeIdentifier);
        return true;
      });
      return [...sampleTypesToAdd, ...sampleTypeCollection];
    }
    return sampleTypeCollection;
  }
}
