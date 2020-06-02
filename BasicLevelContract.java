/*Classe responsável por criar e garantir os dados da tabela inalterados
*Classe "final" para segurança das variáveis
*/

public final class BasicTestContract {

     private BasicTestContract(){}

     public static class QuestionsTable implements BaseColumns {
         public static final String TABLE_NAME = "basic_test_questions";
         public static final String COLUMN_QUESTION = "question";
         public static final String COLUMN_OPTION1 = "option1";
         public static final String COLUMN_OPTION2 = "option2";
         public static final String COLUMN_OPTION3 = "option3";
         public static final String COLUMN_OPTION4 = "option4";
         public static final String COLUMN_ANSWER = "answer";
     }
}
