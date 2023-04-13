import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { OrganismService } from '../service/organism.service';

import { OrganismComponent } from './organism.component';

describe('Organism Management Component', () => {
  let comp: OrganismComponent;
  let fixture: ComponentFixture<OrganismComponent>;
  let service: OrganismService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'organism', component: OrganismComponent }]), HttpClientTestingModule],
      declarations: [OrganismComponent],
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
      .overrideTemplate(OrganismComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganismComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(OrganismService);

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
    expect(comp.organisms?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to organismService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getOrganismIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getOrganismIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
