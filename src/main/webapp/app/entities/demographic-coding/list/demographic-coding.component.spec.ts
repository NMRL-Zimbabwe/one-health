import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DemographicCodingService } from '../service/demographic-coding.service';

import { DemographicCodingComponent } from './demographic-coding.component';

describe('DemographicCoding Management Component', () => {
  let comp: DemographicCodingComponent;
  let fixture: ComponentFixture<DemographicCodingComponent>;
  let service: DemographicCodingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'demographic-coding', component: DemographicCodingComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [DemographicCodingComponent],
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
      .overrideTemplate(DemographicCodingComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemographicCodingComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DemographicCodingService);

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
    expect(comp.demographicCodings?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to demographicCodingService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getDemographicCodingIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getDemographicCodingIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
