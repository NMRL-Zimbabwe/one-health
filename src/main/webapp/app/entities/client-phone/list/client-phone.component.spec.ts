import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ClientPhoneService } from '../service/client-phone.service';

import { ClientPhoneComponent } from './client-phone.component';

describe('ClientPhone Management Component', () => {
  let comp: ClientPhoneComponent;
  let fixture: ComponentFixture<ClientPhoneComponent>;
  let service: ClientPhoneService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'client-phone', component: ClientPhoneComponent }]), HttpClientTestingModule],
      declarations: [ClientPhoneComponent],
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
      .overrideTemplate(ClientPhoneComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClientPhoneComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ClientPhoneService);

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
    expect(comp.clientPhones?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to clientPhoneService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getClientPhoneIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getClientPhoneIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
