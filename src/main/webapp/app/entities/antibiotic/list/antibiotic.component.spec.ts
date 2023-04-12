import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AntibioticService } from '../service/antibiotic.service';

import { AntibioticComponent } from './antibiotic.component';

describe('Antibiotic Management Component', () => {
  let comp: AntibioticComponent;
  let fixture: ComponentFixture<AntibioticComponent>;
  let service: AntibioticService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'antibiotic', component: AntibioticComponent }]), HttpClientTestingModule],
      declarations: [AntibioticComponent],
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
      .overrideTemplate(AntibioticComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AntibioticComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AntibioticService);

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
    expect(comp.antibiotics?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to antibioticService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAntibioticIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAntibioticIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
