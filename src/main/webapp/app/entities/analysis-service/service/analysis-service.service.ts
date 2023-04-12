import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnalysisService, NewAnalysisService } from '../analysis-service.model';

export type PartialUpdateAnalysisService = Partial<IAnalysisService> & Pick<IAnalysisService, 'id'>;

export type EntityResponseType = HttpResponse<IAnalysisService>;
export type EntityArrayResponseType = HttpResponse<IAnalysisService[]>;

@Injectable({ providedIn: 'root' })
export class AnalysisServiceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/analysis-services');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(analysisService: NewAnalysisService): Observable<EntityResponseType> {
    return this.http.post<IAnalysisService>(this.resourceUrl, analysisService, { observe: 'response' });
  }

  update(analysisService: IAnalysisService): Observable<EntityResponseType> {
    return this.http.put<IAnalysisService>(`${this.resourceUrl}/${this.getAnalysisServiceIdentifier(analysisService)}`, analysisService, {
      observe: 'response',
    });
  }

  partialUpdate(analysisService: PartialUpdateAnalysisService): Observable<EntityResponseType> {
    return this.http.patch<IAnalysisService>(`${this.resourceUrl}/${this.getAnalysisServiceIdentifier(analysisService)}`, analysisService, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnalysisService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnalysisService[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnalysisServiceIdentifier(analysisService: Pick<IAnalysisService, 'id'>): number {
    return analysisService.id;
  }

  compareAnalysisService(o1: Pick<IAnalysisService, 'id'> | null, o2: Pick<IAnalysisService, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnalysisServiceIdentifier(o1) === this.getAnalysisServiceIdentifier(o2) : o1 === o2;
  }

  addAnalysisServiceToCollectionIfMissing<Type extends Pick<IAnalysisService, 'id'>>(
    analysisServiceCollection: Type[],
    ...analysisServicesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const analysisServices: Type[] = analysisServicesToCheck.filter(isPresent);
    if (analysisServices.length > 0) {
      const analysisServiceCollectionIdentifiers = analysisServiceCollection.map(
        analysisServiceItem => this.getAnalysisServiceIdentifier(analysisServiceItem)!
      );
      const analysisServicesToAdd = analysisServices.filter(analysisServiceItem => {
        const analysisServiceIdentifier = this.getAnalysisServiceIdentifier(analysisServiceItem);
        if (analysisServiceCollectionIdentifiers.includes(analysisServiceIdentifier)) {
          return false;
        }
        analysisServiceCollectionIdentifiers.push(analysisServiceIdentifier);
        return true;
      });
      return [...analysisServicesToAdd, ...analysisServiceCollection];
    }
    return analysisServiceCollection;
  }
}
