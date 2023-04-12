import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SampleTypeService } from '../service/sample-type.service';

import { SampleTypeComponent } from './sample-type.component';

describe('SampleType Management Component', () => {
  let comp: SampleTypeComponent;
  let fixture: ComponentFixture<SampleTypeComponent>;
  let service: SampleTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'sample-type', component: SampleTypeComponent }]), HttpClientTestingModule],
      declarations: [SampleTypeComponent],
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
      .overrideTemplate(SampleTypeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SampleTypeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SampleTypeService);

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
    expect(comp.sampleTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to sampleTypeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getSampleTypeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getSampleTypeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
