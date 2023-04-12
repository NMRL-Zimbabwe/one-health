import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SectorFormService } from './sector-form.service';
import { SectorService } from '../service/sector.service';
import { ISector } from '../sector.model';

import { SectorUpdateComponent } from './sector-update.component';

describe('Sector Management Update Component', () => {
  let comp: SectorUpdateComponent;
  let fixture: ComponentFixture<SectorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sectorFormService: SectorFormService;
  let sectorService: SectorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SectorUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SectorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SectorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sectorFormService = TestBed.inject(SectorFormService);
    sectorService = TestBed.inject(SectorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sector: ISector = { id: 456 };

      activatedRoute.data = of({ sector });
      comp.ngOnInit();

      expect(comp.sector).toEqual(sector);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISector>>();
      const sector = { id: 123 };
      jest.spyOn(sectorFormService, 'getSector').mockReturnValue(sector);
      jest.spyOn(sectorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sector });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sector }));
      saveSubject.complete();

      // THEN
      expect(sectorFormService.getSector).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sectorService.update).toHaveBeenCalledWith(expect.objectContaining(sector));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISector>>();
      const sector = { id: 123 };
      jest.spyOn(sectorFormService, 'getSector').mockReturnValue({ id: null });
      jest.spyOn(sectorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sector: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sector }));
      saveSubject.complete();

      // THEN
      expect(sectorFormService.getSector).toHaveBeenCalled();
      expect(sectorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISector>>();
      const sector = { id: 123 };
      jest.spyOn(sectorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sector });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sectorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
