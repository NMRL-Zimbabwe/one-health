import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ClientAddressService } from '../service/client-address.service';

import { ClientAddressComponent } from './client-address.component';

describe('ClientAddress Management Component', () => {
  let comp: ClientAddressComponent;
  let fixture: ComponentFixture<ClientAddressComponent>;
  let service: ClientAddressService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'client-address', component: ClientAddressComponent }]), HttpClientTestingModule],
      declarations: [ClientAddressComponent],
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
      .overrideTemplate(ClientAddressComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClientAddressComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ClientAddressService);

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
    expect(comp.clientAddresses?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to clientAddressService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getClientAddressIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getClientAddressIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
