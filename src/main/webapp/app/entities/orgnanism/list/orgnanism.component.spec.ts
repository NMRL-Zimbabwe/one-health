import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { OrgnanismService } from '../service/orgnanism.service';

import { OrgnanismComponent } from './orgnanism.component';

describe('Orgnanism Management Component', () => {
  let comp: OrgnanismComponent;
  let fixture: ComponentFixture<OrgnanismComponent>;
  let service: OrgnanismService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'orgnanism', component: OrgnanismComponent }]), HttpClientTestingModule],
      declarations: [OrgnanismComponent],
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
      .overrideTemplate(OrgnanismComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrgnanismComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(OrgnanismService);

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
    expect(comp.orgnanisms?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to orgnanismService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getOrgnanismIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getOrgnanismIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
