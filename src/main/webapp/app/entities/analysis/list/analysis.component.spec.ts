import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AnalysisService } from '../service/analysis.service';

import { AnalysisComponent } from './analysis.component';

describe('Analysis Management Component', () => {
  let comp: AnalysisComponent;
  let fixture: ComponentFixture<AnalysisComponent>;
  let service: AnalysisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'analysis', component: AnalysisComponent }]), HttpClientTestingModule],
      declarations: [AnalysisComponent],
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
      .overrideTemplate(AnalysisComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnalysisComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnalysisService);

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
    expect(comp.analyses?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to analysisService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAnalysisIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAnalysisIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
