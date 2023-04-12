import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnalysis, NewAnalysis } from '../analysis.model';

export type PartialUpdateAnalysis = Partial<IAnalysis> & Pick<IAnalysis, 'id'>;

type RestOf<T extends IAnalysis | NewAnalysis> = Omit<T, 'dateResulted'> & {
  dateResulted?: string | null;
};

export type RestAnalysis = RestOf<IAnalysis>;

export type NewRestAnalysis = RestOf<NewAnalysis>;

export type PartialUpdateRestAnalysis = RestOf<PartialUpdateAnalysis>;

export type EntityResponseType = HttpResponse<IAnalysis>;
export type EntityArrayResponseType = HttpResponse<IAnalysis[]>;

@Injectable({ providedIn: 'root' })
export class AnalysisService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/analyses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(analysis: NewAnalysis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analysis);
    return this.http
      .post<RestAnalysis>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(analysis: IAnalysis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analysis);
    return this.http
      .put<RestAnalysis>(`${this.resourceUrl}/${this.getAnalysisIdentifier(analysis)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(analysis: PartialUpdateAnalysis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analysis);
    return this.http
      .patch<RestAnalysis>(`${this.resourceUrl}/${this.getAnalysisIdentifier(analysis)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAnalysis>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAnalysis[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnalysisIdentifier(analysis: Pick<IAnalysis, 'id'>): number {
    return analysis.id;
  }

  compareAnalysis(o1: Pick<IAnalysis, 'id'> | null, o2: Pick<IAnalysis, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnalysisIdentifier(o1) === this.getAnalysisIdentifier(o2) : o1 === o2;
  }

  addAnalysisToCollectionIfMissing<Type extends Pick<IAnalysis, 'id'>>(
    analysisCollection: Type[],
    ...analysesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const analyses: Type[] = analysesToCheck.filter(isPresent);
    if (analyses.length > 0) {
      const analysisCollectionIdentifiers = analysisCollection.map(analysisItem => this.getAnalysisIdentifier(analysisItem)!);
      const analysesToAdd = analyses.filter(analysisItem => {
        const analysisIdentifier = this.getAnalysisIdentifier(analysisItem);
        if (analysisCollectionIdentifiers.includes(analysisIdentifier)) {
          return false;
        }
        analysisCollectionIdentifiers.push(analysisIdentifier);
        return true;
      });
      return [...analysesToAdd, ...analysisCollection];
    }
    return analysisCollection;
  }

  protected convertDateFromClient<T extends IAnalysis | NewAnalysis | PartialUpdateAnalysis>(analysis: T): RestOf<T> {
    return {
      ...analysis,
      dateResulted: analysis.dateResulted?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAnalysis: RestAnalysis): IAnalysis {
    return {
      ...restAnalysis,
      dateResulted: restAnalysis.dateResulted ? dayjs(restAnalysis.dateResulted) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAnalysis>): HttpResponse<IAnalysis> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAnalysis[]>): HttpResponse<IAnalysis[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
