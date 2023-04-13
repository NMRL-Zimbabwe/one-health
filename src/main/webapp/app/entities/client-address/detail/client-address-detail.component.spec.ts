import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ClientAddressDetailComponent } from './client-address-detail.component';

describe('ClientAddress Management Detail Component', () => {
  let comp: ClientAddressDetailComponent;
  let fixture: ComponentFixture<ClientAddressDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClientAddressDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ clientAddress: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ClientAddressDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ClientAddressDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load clientAddress on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.clientAddress).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
