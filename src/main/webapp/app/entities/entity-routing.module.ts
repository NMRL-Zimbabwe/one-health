import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'demographic-coding',
        data: { pageTitle: 'oneHealthApp.demographicCoding.home.title' },
        loadChildren: () => import('./demographic-coding/demographic-coding.module').then(m => m.DemographicCodingModule),
      },
      {
        path: 'demography',
        data: { pageTitle: 'oneHealthApp.demography.home.title' },
        loadChildren: () => import('./demography/demography.module').then(m => m.DemographyModule),
      },
      {
        path: 'sample-type',
        data: { pageTitle: 'oneHealthApp.sampleType.home.title' },
        loadChildren: () => import('./sample-type/sample-type.module').then(m => m.SampleTypeModule),
      },
      {
        path: 'analysis-service',
        data: { pageTitle: 'oneHealthApp.analysisService.home.title' },
        loadChildren: () => import('./analysis-service/analysis-service.module').then(m => m.AnalysisServiceModule),
      },
      {
        path: 'sample',
        data: { pageTitle: 'oneHealthApp.sample.home.title' },
        loadChildren: () => import('./sample/sample.module').then(m => m.SampleModule),
      },
      {
        path: 'analysis',
        data: { pageTitle: 'oneHealthApp.analysis.home.title' },
        loadChildren: () => import('./analysis/analysis.module').then(m => m.AnalysisModule),
      },
      {
        path: 'antibiotic',
        data: { pageTitle: 'oneHealthApp.antibiotic.home.title' },
        loadChildren: () => import('./antibiotic/antibiotic.module').then(m => m.AntibioticModule),
      },
      {
        path: 'orgnanism',
        data: { pageTitle: 'oneHealthApp.orgnanism.home.title' },
        loadChildren: () => import('./orgnanism/orgnanism.module').then(m => m.OrgnanismModule),
      },
      {
        path: 'client',
        data: { pageTitle: 'oneHealthApp.client.home.title' },
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
      },
      {
        path: 'location',
        data: { pageTitle: 'oneHealthApp.location.home.title' },
        loadChildren: () => import('./location/location.module').then(m => m.LocationModule),
      },
      {
        path: 'sector',
        data: { pageTitle: 'oneHealthApp.sector.home.title' },
        loadChildren: () => import('./sector/sector.module').then(m => m.SectorModule),
      },
      {
        path: 'district',
        data: { pageTitle: 'oneHealthApp.district.home.title' },
        loadChildren: () => import('./district/district.module').then(m => m.DistrictModule),
      },
      {
        path: 'province',
        data: { pageTitle: 'oneHealthApp.province.home.title' },
        loadChildren: () => import('./province/province.module').then(m => m.ProvinceModule),
      },
      {
        path: 'country',
        data: { pageTitle: 'oneHealthApp.country.home.title' },
        loadChildren: () => import('./country/country.module').then(m => m.CountryModule),
      },
      {
        path: 'laboratory-request',
        data: { pageTitle: 'oneHealthApp.laboratoryRequest.home.title' },
        loadChildren: () => import('./laboratory-request/laboratory-request.module').then(m => m.LaboratoryRequestModule),
      },
      {
        path: 'organism',
        data: { pageTitle: 'oneHealthApp.organism.home.title' },
        loadChildren: () => import('./organism/organism.module').then(m => m.OrganismModule),
      },
      {
        path: 'client-address',
        data: { pageTitle: 'oneHealthApp.clientAddress.home.title' },
        loadChildren: () => import('./client-address/client-address.module').then(m => m.ClientAddressModule),
      },
      {
        path: 'client-phone',
        data: { pageTitle: 'oneHealthApp.clientPhone.home.title' },
        loadChildren: () => import('./client-phone/client-phone.module').then(m => m.ClientPhoneModule),
      },
      {
        path: 'antibiotic-class',
        data: { pageTitle: 'oneHealthApp.antibioticClass.home.title' },
        loadChildren: () => import('./antibiotic-class/antibiotic-class.module').then(m => m.AntibioticClassModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
