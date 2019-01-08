package hpo.api

import builders.dsl.spreadsheet.builder.poi.PoiSpreadsheetBuilder
import grails.gorm.transactions.Transactional
import hpo.api.disease.DbDisease
import hpo.api.gene.DbGene
import hpo.api.term.DbTerm
import org.apache.commons.lang.StringUtils
import org.monarchinitiative.phenol.ontology.data.TermId

@Transactional
class HpoExcelService {

  public static final String SHEET_NAME = "associations"
  public static final String EXCEL_FILE_SUFIX = ".xlsx"
  public static String EXCEL_FILE_PREFIX = ""
  public static String EXCEL_FILENAME = ""

  void setExcelFilePrefixAndName(String prefix){
    EXCEL_FILE_PREFIX = prefix
    EXCEL_FILENAME = EXCEL_FILE_PREFIX + EXCEL_FILE_SUFIX
  }

  File exportExcelGenesFromTerm(OutputStream outs, List<DbGene> genes) {
    File file = File.createTempFile(EXCEL_FILE_PREFIX, EXCEL_FILE_SUFIX)
    PoiSpreadsheetBuilder.create(outs).build {
      sheet(SHEET_NAME) { s ->
        row {
          ["GENE_ENTREZ_ID", "GENE_SYMBOL", "DISEASE_IDS"].each { header ->
            cell {
              value header
            }
          }
        }
        genes.each { gene ->
          row {
            cell(gene.getEntrezGeneId())
            cell(gene.getEntrezGeneSymbol())
            cell(String.join(",", gene.dbDiseases.diseaseId))
          }
        }
      }
    }
    file
  }

  File exportExcelDiseaseFromTerm(OutputStream outs, List<DbDisease> diseases) {
    File file = File.createTempFile(EXCEL_FILE_PREFIX, EXCEL_FILE_SUFIX)
    PoiSpreadsheetBuilder.create(outs).build {
      sheet(SHEET_NAME) { s ->
        row {
          ["DISEASE_ID", "DISEASE_NAME"].each { header ->
            cell {
              value header
            }
          }
        }
        diseases.each { disease ->
          row {
            cell(disease.getDiseaseId())
            cell(disease.getDiseaseName())
          }
        }
      }
    }
    file
  }

  File exportExcelGenesFromDisease(OutputStream outs, Set<DbGene> genes){
    File file = File.createTempFile(EXCEL_FILE_PREFIX, EXCEL_FILE_SUFIX)
    PoiSpreadsheetBuilder.create(outs).build {
      sheet(SHEET_NAME) { s ->
        row {
          ["GENE_ENTREZ_ID", "GENE_SYMBOL"].each { header ->
            cell {
              value header
            }
          }
        }
        genes.each { gene ->
          row {
            cell(gene.getEntrezGeneId())
            cell(gene.getEntrezGeneSymbol())
          }
        }
      }
    }
    file
  }

  File exportExcelTermsFromDisease(OutputStream outs, Set<DbTerm> terms, List categories){
    Map<String, String> catMap = getCategoryForTerm(categories);
    File file = File.createTempFile(EXCEL_FILE_PREFIX, EXCEL_FILE_SUFIX)
    PoiSpreadsheetBuilder.create(outs).build {
      sheet(SHEET_NAME) { s ->
        row {
          ["HPO_TERM_ID", "HPO_TERM_NAME", "CATEGORY"].each { header ->
            cell {
              value header
            }
          }
        }
        terms.each { term ->
          row {
            cell(term.getOntologyId())
            cell(term.getName())
            cell(catMap.get(term.getOntologyId()))
          }
        }
      }
    }
    file
  }

  protected static Map<String, String> getCategoryForTerm(List categoryList){
    /* HP Term to Category */
    Map<String, String> catMap = [:]
    categoryList.each { cat ->
      cat.terms.each { term ->
        catMap.put(term.ontologyId, cat.catLabel)
      }
    }
    return catMap;
  }

  File exportDiseaseFromGene(OutputStream outs, Set<DbDisease> diseases){
    File file = File.createTempFile(EXCEL_FILE_PREFIX, EXCEL_FILE_SUFIX)
    PoiSpreadsheetBuilder.create(outs).build {
      sheet(SHEET_NAME) { s ->
        row {
          ["HPO_TERM_ID", "HPO_TERM_NAME"].each { header ->
            cell {
              value header
            }
          }
        }
        diseases.each { disease ->
          row {
            cell(disease.diseaseId)
            cell(disease.diseaseName)
          }
        }
      }
    }
    file
  }

  File exportTermsFromGene(OutputStream outs, Set<DbTerm> terms){
    File file = File.createTempFile(EXCEL_FILE_PREFIX, EXCEL_FILE_SUFIX)
    PoiSpreadsheetBuilder.create(outs).build {
      sheet(SHEET_NAME) { s ->
        row {
          ["HPO_TERM_ID", "HPO_TERM_NAME"].each { header ->
            cell {
              value header
            }
          }
        }
        terms.each { term ->
          row {
            cell(term.getOntologyId())
            cell(term.getName())
          }
        }
      }
    }
    file
  }
}
