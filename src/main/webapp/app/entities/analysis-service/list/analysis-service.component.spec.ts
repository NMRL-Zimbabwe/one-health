import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AnalysisServiceService } from '../service/analysis-service.service';

import { AnalysisServiceComponent } from './analysis-service.component';

describe('AnalysisService Management Component', () => {
  let comp: AnalysisServiceComponent;
  let fixture: ComponentFixture<AnalysisServiceComponent>;
  let service: AnalysisServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'analysis-service', component: AnalysisServiceComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [AnalysisServiceComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(AnalysisServiceComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnalysisServiceComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnalysisServiceService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.analysisServices?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to analysisServiceService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAnalysisServiceIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAnalysisServiceIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
