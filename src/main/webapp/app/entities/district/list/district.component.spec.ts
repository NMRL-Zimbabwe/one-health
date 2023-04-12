import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DistrictService } from '../service/district.service';

import { DistrictComponent } from './district.component';

describe('District Management Component', () => {
  let comp: DistrictComponent;
  let fixture: ComponentFixture<DistrictComponent>;
  let service: DistrictService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'district', component: DistrictComponent }]), HttpClientTestingModule],
      declarations: [DistrictComponent],
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
      .overrideTemplate(DistrictComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DistrictComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DistrictService);

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
    expect(comp.districts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to districtService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getDistrictIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getDistrictIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
