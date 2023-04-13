import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILaboratoryRequest, NewLaboratoryRequest } from '../laboratory-request.model';

export type PartialUpdateLaboratoryRequest = Partial<ILaboratoryRequest> & Pick<ILaboratoryRequest, 'id'>;

type RestOf<T extends ILaboratoryRequest | NewLaboratoryRequest> = Omit<T, 'dateCollected' | 'dateReceived'> & {
  dateCollected?: string | null;
  dateReceived?: string | null;
};

export type RestLaboratoryRequest = RestOf<ILaboratoryRequest>;

export type NewRestLaboratoryRequest = RestOf<NewLaboratoryRequest>;

export type PartialUpdateRestLaboratoryRequest = RestOf<PartialUpdateLaboratoryRequest>;

export type EntityResponseType = HttpResponse<ILaboratoryRequest>;
export type EntityArrayResponseType = HttpResponse<ILaboratoryRequest[]>;

@Injectable({ providedIn: 'root' })
export class LaboratoryRequestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/laboratory-requests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(laboratoryRequest: NewLaboratoryRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(laboratoryRequest);
    return this.http
      .post<RestLaboratoryRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(laboratoryRequest: ILaboratoryRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(laboratoryRequest);
    return this.http
      .put<RestLaboratoryRequest>(`${this.resourceUrl}/${this.getLaboratoryRequestIdentifier(laboratoryRequest)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(laboratoryRequest: PartialUpdateLaboratoryRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(laboratoryRequest);
    return this.http
      .patch<RestLaboratoryRequest>(`${this.resourceUrl}/${this.getLaboratoryRequestIdentifier(laboratoryRequest)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLaboratoryRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLaboratoryRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLaboratoryRequestIdentifier(laboratoryRequest: Pick<ILaboratoryRequest, 'id'>): number {
    return laboratoryRequest.id;
  }

  compareLaboratoryRequest(o1: Pick<ILaboratoryRequest, 'id'> | null, o2: Pick<ILaboratoryRequest, 'id'> | null): boolean {
    return o1 && o2 ? this.getLaboratoryRequestIdentifier(o1) === this.getLaboratoryRequestIdentifier(o2) : o1 === o2;
  }

  addLaboratoryRequestToCollectionIfMissing<Type extends Pick<ILaboratoryRequest, 'id'>>(
    laboratoryRequestCollection: Type[],
    ...laboratoryRequestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const laboratoryRequests: Type[] = laboratoryRequestsToCheck.filter(isPresent);
    if (laboratoryRequests.length > 0) {
      const laboratoryRequestCollectionIdentifiers = laboratoryRequestCollection.map(
        laboratoryRequestItem => this.getLaboratoryRequestIdentifier(laboratoryRequestItem)!
      );
      const laboratoryRequestsToAdd = laboratoryRequests.filter(laboratoryRequestItem => {
        const laboratoryRequestIdentifier = this.getLaboratoryRequestIdentifier(laboratoryRequestItem);
        if (laboratoryRequestCollectionIdentifiers.includes(laboratoryRequestIdentifier)) {
          return false;
        }
        laboratoryRequestCollectionIdentifiers.push(laboratoryRequestIdentifier);
        return true;
      });
      return [...laboratoryRequestsToAdd, ...laboratoryRequestCollection];
    }
    return laboratoryRequestCollection;
  }

  protected convertDateFromClient<T extends ILaboratoryRequest | NewLaboratoryRequest | PartialUpdateLaboratoryRequest>(
    laboratoryRequest: T
  ): RestOf<T> {
    return {
      ...laboratoryRequest,
      dateCollected: laboratoryRequest.dateCollected?.format(DATE_FORMAT) ?? null,
      dateReceived: laboratoryRequest.dateReceived?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restLaboratoryRequest: RestLaboratoryRequest): ILaboratoryRequest {
    return {
      ...restLaboratoryRequest,
      dateCollected: restLaboratoryRequest.dateCollected ? dayjs(restLaboratoryRequest.dateCollected) : undefined,
      dateReceived: restLaboratoryRequest.dateReceived ? dayjs(restLaboratoryRequest.dateReceived) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLaboratoryRequest>): HttpResponse<ILaboratoryRequest> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLaboratoryRequest[]>): HttpResponse<ILaboratoryRequest[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
