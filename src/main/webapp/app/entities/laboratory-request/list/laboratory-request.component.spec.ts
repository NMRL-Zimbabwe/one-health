import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LaboratoryRequestService } from '../service/laboratory-request.service';

import { LaboratoryRequestComponent } from './laboratory-request.component';

describe('LaboratoryRequest Management Component', () => {
  let comp: LaboratoryRequestComponent;
  let fixture: ComponentFixture<LaboratoryRequestComponent>;
  let service: LaboratoryRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'laboratory-request', component: LaboratoryRequestComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [LaboratoryRequestComponent],
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
      .overrideTemplate(LaboratoryRequestComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LaboratoryRequestComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LaboratoryRequestService);

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
    expect(comp.laboratoryRequests?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to laboratoryRequestService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getLaboratoryRequestIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getLaboratoryRequestIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
