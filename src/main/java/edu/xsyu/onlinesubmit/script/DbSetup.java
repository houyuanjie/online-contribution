package edu.xsyu.onlinesubmit.script;

import edu.xsyu.onlinesubmit.entity.BytesFile;
import edu.xsyu.onlinesubmit.entity.Manuscript;
import edu.xsyu.onlinesubmit.entity.Publication;
import edu.xsyu.onlinesubmit.entity.User;
import edu.xsyu.onlinesubmit.enumeration.Role;
import edu.xsyu.onlinesubmit.repository.FileRepository;
import edu.xsyu.onlinesubmit.repository.ManuscriptRepository;
import edu.xsyu.onlinesubmit.repository.PublicationRepository;
import edu.xsyu.onlinesubmit.repository.UserRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;

import static edu.xsyu.onlinesubmit.enumeration.ManuscriptReviewStatus.Approved;
import static edu.xsyu.onlinesubmit.enumeration.ManuscriptReviewStatus.NotReviewed;

/**
 * 数据库初始化脚本
 */
@Component
public class DbSetup {

    @Resource
    private PublicationRepository publicationRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private FileRepository fileRepository;
    @Resource
    private ManuscriptRepository manuscriptRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 规定执行顺序
     */
    @PostConstruct
    public void run() throws IOException {
        insertNewUsers();
        insertNewPublications();
        insertNewManuscripts();
    }

    /**
     * 向数据库插入新的稿件
     */
    @Transactional
    public void insertNewManuscripts() throws IOException {
        // 读入文件, 所有的稿件都用这个用于测试的文件
        var fileName = "file.docx";
        var filePath = "/document/manuscriptFile/" + fileName;
        var classPathResource = new ClassPathResource(filePath);

        var publications = publicationRepository.findAll();
        for (var publication : publications) {
            for (int i = 1; i <= 3; ++i) {
                var title = publication.getName() + "_测试_" + i;
                var file = title + ".docx";
                var manuscript = new Manuscript();
                manuscript.setTitle(title);
                manuscript.setAuthor("测试作者");
                manuscript.setKeywords("测试关键字");
                manuscript.setOrganization("测试单位");
                manuscript.setSummary("测试摘要");
                manuscript.setPublication(publication);
                if (i == 3) {
                    manuscript.setReviewStatus(NotReviewed);
                } else {
                    manuscript.setReviewStatus(Approved);
                }

                // 保存
                try (var inputStream = classPathResource.getInputStream()) {
                    var bytes = inputStream.readAllBytes();
                    var bytesFile = new BytesFile();
                    bytesFile.setBytes(bytes);
                    bytesFile.setSize((long) bytes.length);
                    bytesFile.setContentType("MS-WORD/DOCX");
                    bytesFile.setFileName(file);
                    fileRepository.save(bytesFile);
                    manuscript.setBytesFile(bytesFile);
                    manuscriptRepository.save(manuscript);
                }
            }
        }

    }

    /**
     * 向数据库插入新的期刊
     */
    @Transactional
    public void insertNewPublications() throws IOException {
        // 新建对象
        var p1 = new Publication();
        // 名字
        p1.setName("统计与决策");
        //国际刊号
        p1.setIssn("ISSN 1002-6487");
        // 分类
        p1.setCategory("社会科学");
        //刊期
        p1.setPublicationFrequency("半月刊");
        //语种
        p1.setLanguage("中文");
        //主办单位
        p1.setOrganizer("湖北省统计局统计科学研究所");
        // 简介, 只需要第一段
        p1.setInfo("《统计与决策》杂志社以经济类、统计类、管理类文章为主、立足统计理论与方法的创新，经济数学方法的应用；重点关注经济热点、难点问题的实证与对策思考；对各类经济现象的数量考证和决策建议；社会科学发展和和谐社会发展中各类统计考评指标体系的构建；财经领域以及企业管理中的决策与方法的运用及新知识的推介。《统计与决策》是北大核心期刊；2009年CSSCI来源期刊。");
        // 保存对象
        publicationRepository.save(p1);

        // 剩下的同理
        var p2 = new Publication();
        p2.setName("计算机光盘软件与应用");
        p2.setIssn("ISSN 1007-9599");
        p2.setCategory("信息科技");
        p2.setPublicationFrequency("旬刊");
        p2.setLanguage("中文");
        p2.setOrganizer("中国大恒电子音像出版社");
        p2.setInfo("《计算机光盘软件与应用》杂志是由中国科学院主管、大恒电子音像出版社主办的国内外公开发行的综合性国家级学术期刊。本刊致力于创办以创新、准确、实用为特色，突出综述性、科学性、实用性，及时报道国内外计算机技术在科研、教学、应用方面的研究成果和发展动态，为国内计算机同行提供学术交流的平台。");
        publicationRepository.save(p2);

        // ↓ 在下面继续添加

        var p3 = new Publication();
        p3.setName("作文通讯");
        p3.setIssn("ISSN 1003-7357");
        p3.setCategory("教科文艺");
        p3.setPublicationFrequency("半月刊");
        p3.setLanguage("中文");
        p3.setOrganizer("中国大恒电子音像出版社");
        p3.setInfo("《作文通讯》杂志创办于1979年，是中国第1家中学生作文精品期刊，也是新闻出版总署推荐的中国优秀少儿报刊、天津市优秀期刊。以全国著名的十三所重点中学（包括东北师大附中、北大附中、人大附中、景山学校、天津南开中学、南京师大附中、苏州中学、杭州学军中学、华东师大一附中、华东师大二附中、上海师大附中、福州一中、福州三中等）为核心团队，全国中语界百余位作文教学专家名师组成强大的编创阵容，集权威性、示范性、精品性于一体。");
        publicationRepository.save(p3);

        var p4 = new Publication();
        p4.setName("少年儿童研究");
        p4.setIssn("ISSN 1002-9915");
        p4.setCategory("教科文艺");
        p4.setPublicationFrequency("月刊");
        p4.setLanguage("中文");
        p4.setOrganizer("中国青少年研究中心;中国少年先锋队工作学会;中国青年政治学院");
        p4.setInfo("《贵州广播电视大学学报》编辑部紧紧围绕把电大建设成有中国特色的现代远程开放大学这一目标，根据21世纪广播电视大学改革发展的指导思想和基本思路，以开放性、教育现代化两大命题为中心，积极探索远程教育在实践中的新思路、新问题，开展了多形式多层次的学术研究活动，取得了明显的效果。");
        publicationRepository.save(p4);

        var p5 = new Publication();
        p5.setName("商情");
        p5.setIssn("ISSN 1673-4041");
        p5.setCategory("经济管理");
        p5.setPublicationFrequency("周刊");
        p5.setLanguage("中文");
        p5.setOrganizer("河北消费时尚文化传播中心");
        p5.setInfo("商情》杂志社以发展提高经济、管理、教育等科研成果和学术水平，开展具有特色的学术交流，为促进中国现代化建设提出建议，以刊载知名专家学者的学术文章及研究人员的学术论文为主，为国家机关、学术研究机构、企事业单位、高等院校的广大学者、科研人员等发表科研成果及学术研究探索提供一个展示的平台。《商情》编辑部依托大流通、大商业、大市场，面向社会、面向企业、面向现代商人、面向科研院所、面向高等院校，深入开展市场经济理论、流通经济与管理的理论与实践研究，建立和完善社会主义市场经济体制，积极推动现代流通经济的发展，繁荣现代流通业和市场。");
        publicationRepository.save(p5);

        var p6 = new Publication();
        p6.setName("中国药理学通报");
        p6.setIssn("ISSN 1001-1978");
        p6.setCategory("基础科学");
        p6.setPublicationFrequency("月刊");
        p6.setLanguage("中文");
        p6.setOrganizer("中国药理学会");
        p6.setInfo("《中国药理学通报》杂志社始终秉承“跟踪学科前沿、揭示药理进展、融合校——院——企、服务产——学——研”的办刊宗旨，始终坚守“靠质量立刊、凭特色发展”的办刊理念，坚持“差异化可持续”发展模式和“校刊一体”的运行机制。读者对象主要是药理学、药学及其他相关专业的研究工作者，各级临床医师、药师、制药界科技人员。");
        publicationRepository.save(p6);

        var p7 = new Publication();
        p7.setName("学前教育");
        p7.setIssn("ISSN 1000-4130");
        p7.setCategory("社会科学");
        p7.setPublicationFrequency("半月刊");
        p7.setLanguage("中文");
        p7.setOrganizer("北京市教育音像报刊总社");
        p7.setInfo("《学前教育》杂志社以\"幼教工作者的亲密助手，学前儿童家长的有益读物\"为办刊宗旨和优良传统，《学前教育》编辑部以促进教师与幼儿健康成长、快乐生活为理想和目标，赢得了广大读者的肯定和追随，成为幼教工作者的必读参考。下设主要栏目有：视界、教研、话题、专栏、课程、诊断等栏目。");
        publicationRepository.save(p7);

        var p8 = new Publication();
        p8.setName("中国水运.航道科技");
        p8.setIssn("ISSN 1006-7973");
        p8.setCategory("工程科技");
        p8.setPublicationFrequency("双月刊");
        p8.setLanguage("中文");
        p8.setOrganizer("长江航务管理局");
        p8.setInfo("《中国水运.航道科技》杂志社的主办单位长江航道局是隶属于中华人民共和国交通运输部的大型公益性事业单位,主要从事长江干线航道规划、建设、管理、养护等方面的工作。");
        publicationRepository.save(p8);

        var p9 = new Publication();
        p9.setName("西部广播电视");
        p9.setIssn("ISSN 1006-5628");
        p9.setCategory("信息科技");
        p9.setPublicationFrequency("半月刊");
        p9.setLanguage("中文");
        p9.setOrganizer("四川省广播电视新闻与传播研究所");
        p9.setInfo("《西部广播电视》杂志社创刊以来，面向社会、面向基层，宣传报道广播电视技术发展方针政策，推广新技术、新产品，为广播电视行业的技术人员服务。《西部广播电视》编辑部立足于广播电视行业的技术优势，以“实用性、科学性、指导性、可读性”为办刊特色，其主要内容为广播电视技术发展、技术标准及科技管理、广播电视网络建设、广播电视数字化等，促进西部地区广播电视技术水平的提高。");
        publicationRepository.save(p9);

        var p10 = new Publication();
        p10.setName("山东畜牧兽医");
        p10.setIssn("ISSN 1007-1733");
        p10.setCategory("农业科技");
        p10.setPublicationFrequency("月刊");
        p10.setLanguage("中文");
        p10.setOrganizer("山东畜牧鲁医学会;山东农业大学");
        p10.setInfo("《山东畜牧兽医》杂志主要报道畜牧兽医方面的最新研究动态、畜牧生产和兽医临床技术等，为促进我国畜牧兽医事业的发展做贡献。《山东畜牧兽医》杂志2008年获第六届全国畜牧兽医优秀期刊一等奖、2009年山东省科学技术协会优秀期刊、1998年市十佳期刊、2000年市十佳期刊、1996年被山东省科学技术学会、山东省新闻出版局评为优良自然科学技术期刊。");
        publicationRepository.save(p10);

        var p11 = new Publication();
        p11.setName("上海蔬菜");
        p11.setIssn("ISSN 1002-1469");
        p11.setCategory("农业科技");
        p11.setPublicationFrequency("双月刊");
        p11.setLanguage("中文");
        p11.setOrganizer("上海蔬菜经济研究会;上海市农业科学院");
        p11.setInfo("《上海蔬菜》杂志社本着立足华东、面向全国蔬菜从业者的原则，致力于为决策者提供参考、为生产者提供技术、为经营者提供信息，以全面服务蔬菜产业为办刊宗旨，读者对象主要是广大蔬菜生产者、经营者、农技推广人员、院校师生、农业主管领导等。");
        publicationRepository.save(p11);

        var p12 = new Publication();
        p12.setName("健康向导");
        p12.setIssn("ISSN 1006-9038");
        p12.setCategory("医药卫生");
        p12.setPublicationFrequency("双月刊");
        p12.setLanguage("中文");
        p12.setOrganizer("山西省医学会");
        p12.setInfo("《健康向导》杂志为山西省卫生厅主管，山西省医学会主办的科普期刊。以传播卫生信息，普及医药知识，服务大众健康，推广科学生活方式，引导百姓幸福生活为宗旨。编辑部设有健康教育进万家、本刊特稿、专题报道、专家论病、防病治病、日常保健、健康理念、饮食保健、祖国医药、艾滋病健康教育、心理健康、生活妙招、乡村卫生园地、运动健身、美容时尚等栏目。");
        publicationRepository.save(p12);

        var p13 = new Publication();
        p13.setName("健康生活");
        p13.setIssn("ISSN 1005-6645");
        p13.setCategory("医药卫生");
        p13.setPublicationFrequency("月刊");
        p13.setLanguage("中文");
        p13.setOrganizer("广西壮族自治区健康教育所");
        p13.setInfo("《健康生活》杂志社的宗旨是传播保健知识，提高人们的自我保健意识和能力，提高人们的健康水平和生活质量，改变不良的生活习惯，使人们达到健康长寿的目的。");
        publicationRepository.save(p13);

        var p14 = new Publication();
        p14.setName("钟山风雨");
        p14.setIssn("ISSN 1009-9077");
        p14.setCategory("哲学政法");
        p14.setPublicationFrequency("双月刊");
        p14.setLanguage("中文");
        p14.setOrganizer("江苏省政协文史委员会");
        p14.setInfo("《钟山风雨》杂志创刊于2001年，是经国家新闻出版署批准，由江苏省政协主管、江苏省政协文史委员会主办的文史刊物。本刊为江苏一级期刊，曾被评为“优秀期刊”入选江苏期刊方阵，以鲜明的个性特色赢得政协委员、各界人士与广大读者的好评。");
        publicationRepository.save(p14);

        var p15 = new Publication();
        p15.setName("音乐探索");
        p15.setIssn("ISSN 1004-2172");
        p15.setCategory("哲学政法");
        p15.setPublicationFrequency("季刊");
        p15.setLanguage("中文");
        p15.setOrganizer("四川音乐学院");
        p15.setInfo(" 《音乐探索》本刊被北大1992版核心期刊、剑桥科学文摘社ProQeust数据库收录、国家哲学社会科学学术期刊数据库收录，2008-2013年CSSCI来源期刊；连续两届(2011-2014年)被评为\"RCCSE中国核心学术期刊\"(A级)；2010年获“全国高校优秀社科期刊一等奖“；2010年在四川高校学报研究会文科分会评优活动中获一等奖,其中\"王光祈栏目\"获优秀栏目奖。");
        publicationRepository.save(p15);

        var p16 = new Publication();
        p16.setName("世界哲学");
        p16.setIssn("ISSN 1671-4318");
        p16.setCategory("哲学政法");
        p16.setPublicationFrequency("双月刊");
        p16.setLanguage("中文");
        p16.setOrganizer("中国社会科学院哲学研究所");
        p16.setInfo("《世界哲学》本刊收录情况：CSSCI 南大核心期刊(含扩展版)、万方收录(中)、上海图书馆馆藏、北大核心期刊(中国人文社会科学核心期刊)、国家图书馆馆藏、知网收录(中)、维普收录(中)、中国期刊全文数据库（CJFD）、中国核心期刊遴选数据库、全国中文核心期刊。");
        publicationRepository.save(p16);

        var p17 = new Publication();
        p17.setName("中国远程教育");
        p17.setIssn("ISSN 1009-458X");
        p17.setCategory("教科文艺");
        p17.setPublicationFrequency("月刊");
        p17.setLanguage("中文");
        p17.setOrganizer("中央广播电视大学");
        p17.setInfo(" 《中国远程教育》本刊主要介绍国内外广播电视教育现状，交流办学经验，开展电大教育理论和探讨。是国家哲学社会科学学术期刊数据库收录期刊。");
        publicationRepository.save(p17);

        var p18 = new Publication();
        p18.setName("中国特殊教育");
        p18.setIssn("ISSN 1007-3728");
        p18.setCategory("教科文艺");
        p18.setPublicationFrequency("月刊");
        p18.setLanguage("中文");
        p18.setOrganizer("中央教育科学研究所");
        p18.setInfo(" 《中国特殊教育》编辑部以特殊需要人群为服务对象，所刊载的文章主要反映我国特殊儿童心理与教育研究、教学领域的最新成果与进展，以展示我国特殊教育研究领域最高水平学术成果为宗旨，保持学术刊物的权威性和指导性。作为反映我国特殊教育研究最高水平的主要窗口，不仅在中国特殊教育界享有很高的声誉，在国际上也有一定影响。");
        publicationRepository.save(p18);

        var p19 = new Publication();
        p19.setName("技术与市场");
        p19.setIssn("ISSN 1006-8554");
        p19.setCategory("经济管理");
        p19.setPublicationFrequency("月刊");
        p19.setLanguage("中文");
        p19.setOrganizer("四川省科技信息研究所");
        p19.setInfo("《技术与市场》杂志社创刊以来，以园林绿化科技和景观工程实践为内容，以园林工程成功案例为素材，集园林艺术、景观、园林工程立体规划为一体，捕捉国内外花卉、苗木市场动态，提供准确的市场和商业信息为载体，得到了广大园林专家学者、园林企业管理人员、项目经理、工程设计师及其园林爱好者的认可和支持。《技术与市场》编辑部以报道各行各业技术创新为主线，为企业在研发与管理方面以及提升企业核心竞争力方面提供有效支撑。同时，本刊面向市场，传递国内外科技信息，将全国科技工作者开发出来的技术成果迅速推向市场，为推动科技进步和技术创新服务。");
        publicationRepository.save(p19);

        var p20 = new Publication();
        p20.setName("幼儿100(教师版)");
        p20.setIssn("ISSN 1674-182X");
        p20.setCategory("社会科学");
        p20.setPublicationFrequency("月刊");
        p20.setLanguage("中文");
        p20.setOrganizer("江苏凤凰少年儿童出版社有限公司");
        p20.setInfo("《幼儿100》杂志社始终坚持“以儿童终身的可持续发展为本，把握最新的教育理念，最大限度地促进幼儿身心和谐、富有个性地发展”的办刊方针。《幼儿100》编辑部拥有一流的幼教专家、文字作者和绘画作者队伍，使全书图文并茂，带给读者美好的阅读体验。");
        publicationRepository.save(p20);

        var p21 = new Publication();
        p21.setName("中小学数字化教学");
        p21.setIssn("ISSN 2096-4234");
        p21.setCategory("教科文艺");
        p21.setPublicationFrequency("月刊");
        p21.setLanguage("中文");
        p21.setOrganizer("人民教育出版社");
        p21.setInfo("《中小学数字化教学》编辑部办刊理念是“面向教育现代化 引领数字化教学”。做教师的良师益友、教研员的亲密战友、校长的参谋助手是我们的使命和职责。坚持创新服务教育，以“纸数联动”融合出版引领发展，为教育、出版和技术的深度融合提供学术交流和发展平台，以及优质的互联网+教育的知识服务。");
        publicationRepository.save(p21);

        var p22 = new Publication();
        p22.setName("中小学实验与装备");
        p22.setIssn("ISSN 1673-6869");
        p22.setCategory("教科文艺");
        p22.setPublicationFrequency("双月刊");
        p22.setLanguage("中文");
        p22.setOrganizer("湖北省教育技术装备处");
        p22.setInfo("《中小学实验与装备》杂志是双月刊，创刊于1991年，是由湖北省教育厅主管、经国家科委及国家新闻出版部门批准的国内外公开发行的期刊，是反映教育技术装备的专业性期刊。");
        publicationRepository.save(p22);

        var p23 = new Publication();
        p23.setName("世界经济");
        p23.setIssn("ISSN 1002-9621");
        p23.setCategory("经济管理");
        p23.setPublicationFrequency("月刊");
        p23.setLanguage("中文");
        p23.setOrganizer("中国社会科学院世界经济与政治研究所;中国世界经济学会");
        p23.setInfo("《世界经济》杂志社创刊以来，一直坚持“理论性、战略性、综合性和现实性”的办刊宗旨，介绍和剖析世界经济形势及动态，研究和探讨当前经济领域的热点问题及国外经济发展趋势，在高等院校、研究机构、政府决策部门和企业拥有众多读者。《世界经济》编辑部注重学术积淀和创新，注重理论联系实际，力争运用恰当的分析方法研究贴近时代脉搏的重大国内外经济问题，注重方法和理论的研究，力争更好地服务于高校和科研机构中科研人员、教师及研究生的科研工作。");
        publicationRepository.save(p23);

        var p24 = new Publication();
        p24.setName("审计与经济研究");
        p24.setIssn("ISSN 1004-4833");
        p24.setCategory("经济管理");
        p24.setPublicationFrequency("双月刊");
        p24.setLanguage("中文");
        p24.setOrganizer("南京审计大学");
        p24.setInfo("《审计与经济研究》杂志社创刊以来，一直坚持“繁荣我国审计事业、促进国内外审计学和会计学学术交流”的办刊宗旨，研究审计理论和其他经济管理理论，并注重理论联系实践，主要发表理论、审计工作研究及其他经济与管理方面的论文、调研报告、译文或国内外学术动态。《审计与经济研究》编辑部秉承创办学术型刊物、以审计会计为核心，打造特色栏目的理念，不断强化期刊的学术性，通过原有栏目的重组和新的特色栏目的创办，不断提高期刊的学术质量和社会影响力。");
        publicationRepository.save(p24);

        var p25 = new Publication();
        p25.setName("时间频率公报");
        p25.setIssn("ISSN 1001-1811");
        p25.setCategory("基础科学");
        p25.setPublicationFrequency("月刊");
        p25.setLanguage("中文");
        p25.setOrganizer("中国科学院国家授时中心");
        p25.setInfo("《时间频率公报》杂志创刊于1979年，由中国科学院主管、中国科学院国家授时中心（原陕西天文台）主办。中国科学院国家授时中心承担国家的授时任务，保持着我国高精度的原子时基准，通过专用长、短波授时台发播我国的标准时间与标准频率信号，并通过本刊向用户提供广泛的授时业务信息，包括我国BPL长波授时台时间信号、BPM短波授时台的UTC（记为BPMC）和UT1(记为BPM1)时号、中央电视台在我国广播卫星转发的电视信号中插入的时间信号，以及美国导航星全球定位系统（GPS）的时间信号等，相对国家授时中心协调世界时系统UTC（NTSC）的发播时间。");
        publicationRepository.save(p25);

        var p26 = new Publication();
        p26.setName("南京大学学报(数学半年刊)");
        p26.setIssn("ISSN 0469-5097");
        p26.setCategory("基础科学");
        p26.setPublicationFrequency("季刊");
        p26.setLanguage("中文");
        p26.setOrganizer("南京大学");
        p26.setInfo("《南京大学学报(数学半年刊)》杂志是由南京大学主办的综合性数学学术期刊，涵盖数学学科的所有分支，主要刊登数学各个分支的创造性论文、研究简报和应用成果等方面的论文。");
        publicationRepository.save(p26);

        var p27 = new Publication();
        p27.setName("Plant Diversity");
        p27.setIssn("ISSN 2096-2703");
        p27.setCategory("基础科学");
        p27.setPublicationFrequency("双月刊");
        p27.setLanguage("中文");
        p27.setOrganizer("中国科学院昆明植物研究所;中国植物学会");
        p27.setInfo("《Plant Diversity》杂志是由中国科学院昆明植物研究所和中国植物学会共同创办的植物学专业学术期刊，从创刊至今已有近40年历史。");
        publicationRepository.save(p27);

        var p28 = new Publication();
        p28.setName("幼儿美术");
        p28.setIssn("ISSN 2096-4463");
        p28.setCategory("社会科学");
        p28.setPublicationFrequency("双月刊");
        p28.setLanguage("中文");
        p28.setOrganizer("中国美术出版总社有限公司");
        p28.setInfo("《幼儿美术》杂志社的办刊宗旨是重点为幼儿园教师提供专业的高质量服务，也为校外美术培训机构教师和对该领域感兴趣的家长，提供了解国内外幼儿美术启蒙教育理论与实践发展动态的平台。《幼儿美术》编辑部面向广大致力于幼儿美术启蒙教育的专家学者和园所内外一线美术教学人员长期征稿。");
        publicationRepository.save(p28);

        var p29 = new Publication();
        p29.setName("中小学班主任");
        p29.setIssn("ISSN 2096-3742");
        p29.setCategory("社会科学");
        p29.setPublicationFrequency("半月刊");
        p29.setLanguage("中文");
        p29.setOrganizer("上海世纪出版股份有限公司科技教育出版社");
        p29.setInfo("《中小学班主任》杂志社以为全国中小学教师提供学术指导，促进专业发展服务，是全国一线教师和教育研究者展示专业智慧和工作成果的平台为办刊宗旨。《中小学班主任》编辑部主要围绕班主任的价值认同、角色体验、学术研究、专业发展、实践智慧等方面的个性主张、精练评述或真情感悟提炼主题。");
        publicationRepository.save(p29);

        var p30 = new Publication();
        p30.setName("开放教育研究");
        p30.setIssn("ISSN 1007-2179");
        p30.setCategory("社会科学");
        p30.setPublicationFrequency("双月刊");
        p30.setLanguage("中文");
        p30.setOrganizer("上海远程教育集团;上海电视大学");
        p30.setInfo("《开放教育研究》杂志社立足于远程教育，以开放的理念包容学派，以创新的思想总结理论，以务实的态度关注实践。《开放教育研究》编辑部集中了国内外开放与远程教育领域的一流专家，组成了一支高水平，强阵容的顾问委员会和专家委员会。");
        publicationRepository.save(p30);

        var p31 = new Publication();
        p31.setName("交通运输系统工程与信息");
        p31.setIssn("ISSN 1009-6744");
        p31.setCategory("工程科技");
        p31.setPublicationFrequency("双月刊");
        p31.setLanguage("中文");
        p31.setOrganizer("中国系统工程协会");
        p31.setInfo(" 《交通运输系统工程与信息》杂志以传播新技术、促进学术交流、推动学科发展为宗旨，坚持深度与广度、理论与应用、引进与创新相结合的方针，努力反映交通运输系统工程、智能交通与信息等领域的最新成就，并密切注意世界交通运输科技前沿的发展动向，积极宣传交通运输与系统工程等新兴学科的理论和思想，鼓励不同观点的争鸣。");
        publicationRepository.save(p31);

        var p32 = new Publication();
        p32.setName("岩石力学与工程学报");
        p32.setIssn("ISSN 1000-6915");
        p32.setCategory("工程科技");
        p32.setPublicationFrequency("月刊");
        p32.setLanguage("中文");
        p32.setOrganizer("中国岩石力学与工程学会");
        p32.setInfo("《岩石力学与工程学报》杂志为全国中文核心期刊、中国科协精品科技期刊、中国百强报刊、中国最具国际影响力学术期刊、百种中国杰出学术期刊、湖北省十大名刊及EI核心收录期刊，现被EI、《英国剑桥文摘》(CSA)、《日本科学技术社数据库》和CSCD等国内外权威数据库收录。");
        publicationRepository.save(p32);

        var p33 = new Publication();
        p33.setName("电力自动化设备");
        p33.setIssn("ISSN 1006-6047");
        p33.setCategory("工程科技");
        p33.setPublicationFrequency("月刊");
        p33.setLanguage("中文");
        p33.setOrganizer("南京电力自动化研究所有限公司");
        p33.setInfo("  《电力自动化设备》杂志是国内外公开发行的专业科技期刊，创刊以来一直坚持面向科研、面向制造、面向应用的办刊方向。《电力自动化设备》杂志社非常重视学术水平及编辑出版质量，在读者中的影响越来越大。");
        publicationRepository.save(p33);

        var p34 = new Publication();
        p34.setName("传媒论坛");
        p34.setIssn("ISSN：2096-5079");
        p34.setCategory("信息科技");
        p34.setPublicationFrequency("半月刊");
        p34.setLanguage("中文");
        p34.setOrganizer("江西日报社");
        p34.setInfo("《传媒论坛》杂志社创刊以来，坚持正确的办刊方向和舆论导向，传播新闻传媒理论与实践，提供学术交流平台，为培养新时期传媒人才服务，为传统媒体与新兴媒体融合发展服务。《传媒论坛》编辑部理论联系实践，以从事信息传播、新闻传播、广播及电视事业、网络传播、图书及出版事业、群众文化事业的工作者，传媒事业的管理者、研究人员和相关大中专院校教育工作者的传媒类稿件为主，为进一步发展我国的传媒事业提供学术上的宣传与交流阵地。");
        publicationRepository.save(p34);

        var p35 = new Publication();
        p35.setName("中国科技期刊研究");
        p35.setIssn("ISSN 1001-7143");
        p35.setCategory("信息科技");
        p35.setPublicationFrequency("月刊");
        p35.setLanguage("中文");
        p35.setOrganizer("中国科学院自然科学期刊编辑研究会");
        p35.setInfo(" 《中国科技期刊研究》杂志社创刊以来，本刊一直坚持“一个中心，两个基本点”的办刊宗旨，领先和团结广大科技工作者科技期刊编辑出版工作者，开展科技期刊研究，促进期刊事业繁荣。《中国科技期刊研究》编辑部以新观点、新方法、新材料为主题，理论联系实践，推动科技进步，为社会主义现代化建设服务。");
        publicationRepository.save(p35);

        var p36 = new Publication();
        p36.setName("蔬菜");
        p36.setIssn("ISSN 1001-8336");
        p36.setCategory("农业科技");
        p36.setPublicationFrequency("月刊");
        p36.setLanguage("中文");
        p36.setOrganizer("北京农业科学院农业科技信息研究所");
        p36.setInfo("《蔬菜》杂志是一册面向全国公开发行的蔬菜专业类杂志，是“中国期刊全文数据库（CJDF）全文收录期刊”、“中国核心期刊（遴选）数据库来源期刊”、“中国学术期刊综合评价数据库（CAJCFD）统计源期刊”。杂志先后荣获了“全国优秀农业期刊”、“北方优秀期刊”、“北京市优秀期刊”等称号。");
        publicationRepository.save(p36);

        var p37 = new Publication();
        p37.setName("温带林业研究");
        p37.setIssn("ISSN 2096-4900");
        p37.setCategory("农业科技");
        p37.setPublicationFrequency("季刊");
        p37.setLanguage("中文");
        p37.setOrganizer("国家林业局哈尔滨机械研究所");
        p37.setInfo("《温带林业研究》杂志办刊宗旨为立足生态修复与建设的林业发展模式，刊发温带林业保护、森林生态、森林培育、林林遗传育种等学科研究成果，服务生态林业和民生林业发展，中国知网、万方数据库、维普网均收录。");
        publicationRepository.save(p37);

        var p38 = new Publication();
        p38.setName("现代农业");
        p38.setIssn("ISSN 1008-0708");
        p38.setCategory("农业科技");
        p38.setPublicationFrequency("月刊");
        p38.setLanguage("中文");
        p38.setOrganizer("内蒙古自治区农业厅");
        p38.setInfo("《现代农业》杂志自1975年创刊以来，始终坚持以宣传党的方针政策，传播农业科学知识，为农业、农民服务为办刊宗旨，主要报道国内外的农业新技术、新成果，介绍实用技术，刊载专家学者对疑难问题的解答，传递市场信息，为广大农牧民脱贫致富达小康服务。");
        publicationRepository.save(p38);

        var p39 = new Publication();
        p39.setName("国外医学(微生物学分册)");
        p39.setIssn("ISSN 1001-1129");
        p39.setCategory("医药卫生");
        p39.setPublicationFrequency("双月刊");
        p39.setLanguage("中文");
        p39.setOrganizer("复旦大学");
        p39.setInfo("《国外医学(微生物学分册)》杂志社以马列主义、毛泽东思想、邓小平理论和“三个代表”重要思想为指导，全面贯彻党的教育方针和“双百方针”，理论联系实际，开展教育科学研究和学科基础理论研究，交流科技成果，促进学院教学、科研工作的发展，为教育改革和社会主义现代化建设做出贡献。旨在建立一个学科间交叉﹑基础联系临床和公共卫生预防的平台，以利于传播科研成果，培养学科人才和促进国内外学术交流。");
        publicationRepository.save(p39);

        var p40 = new Publication();
        p40.setName("肝博士");
        p40.setIssn("ISSN 1673-0550");
        p40.setCategory("医药卫生");
        p40.setPublicationFrequency("双月刊");
        p40.setLanguage("中文");
        p40.setOrganizer("重庆医科大学生附属第二医院");
        p40.setInfo("《肝博士》杂志社理论联系实际，开展教育科学研究和学科基础理论研究，交流科技成果，促进学院教学、科研工作的发展，为教育改革和社会主义现代化建设做出贡献。");
        publicationRepository.save(p40);

        var p41 = new Publication();
        p41.setName("东方食疗与保健");
        p41.setIssn("ISSN 1672-5018");
        p41.setCategory("医药卫生");
        p41.setPublicationFrequency("月刊");
        p41.setLanguage("中文");
        p41.setOrganizer("湖南省药膳食疗研究会");
        p41.setInfo("《东方食疗与保健》杂志由国际东方药膳食疗学会会长谭兴贵先生创办，谭教授以他在药膳食疗界累积三十多年的专业知识及素养，不遗余力地提升中国药膳食疗的科学性、实用性，并且将中国食疗推向国际舞台，使《东方食疗与保健》杂志成为国际食疗的领导刊物。");
        publicationRepository.save(p41);

        var p42 = new Publication();
        p42.setName("中共党史研究");
        p42.setIssn("ISSN 1003-3815");
        p42.setCategory("哲学政法");
        p42.setPublicationFrequency("月刊");
        p42.setLanguage("中文");
        p42.setOrganizer("中共党史研究室");
        p42.setInfo("《中共党史研究》本刊分别于1999年、2003年、2005年连续获得第一届、第二届、第三届国家期刊奖。国家期刊奖是我国期刊界的最高奖，也是惟一的政府奖。杂志是全国社科类期刊中为数不多的连续三次获得国家期刊奖的刊物之一。这是从社会影响、学术地位、内容质量、编辑质量、版式设计等各方面对《中共党史研究》杂志的全面肯定，由此进一步确立了该刊在我国期刊界的地位。");
        publicationRepository.save(p42);

        var p43 = new Publication();
        p43.setName("中国法学杂志");
        p43.setIssn("ISSN 1003-1707");
        p43.setCategory("哲学政法");
        p43.setPublicationFrequency("双月刊");
        p43.setLanguage("中文");
        p43.setOrganizer("中国法学会");
        p43.setInfo("《中国法学》本刊不断推出新人新作，成为我国培养造就法学研究学术带头人的摇篮。发表了大量最新和最重要的法学学术研究成果，所发表的成果代表了中国法学界最高水平，对于繁荣和发展我国的法学理论、传承法律文化、促进国内外法学交流发挥了重要作用。");
        publicationRepository.save(p43);

        var p44 = new Publication();
        p44.setName("电视指南");
        p44.setIssn("ISSN 1673-7547");
        p44.setCategory("哲学政法");
        p44.setPublicationFrequency("旬刊");
        p44.setLanguage("中文");
        p44.setOrganizer("中国广播影视出版社");
        p44.setInfo("《电视指南》编辑部以打造中国电视剧和电影研究风向标为理念，以中国电视剧和电影大数据为内容核心，为中国电视剧和电影的发展搭建一个有效沟通交流平台为宗旨。");
        publicationRepository.save(p44);

        var p45 = new Publication();
        p45.setName("中小学音乐教育");
        p45.setIssn("ISSN 1002-7580");
        p45.setCategory("教科文艺");
        p45.setPublicationFrequency("月刊");
        p45.setLanguage("中文");
        p45.setOrganizer("浙江省音乐家协会");
        p45.setInfo("《中小学音乐教育》编辑部为广大师生设立了\"音教畅想\"、\"创意课堂\"、\"教案精选\"、\"音乐知识\"、\"校园旋律\"等专栏，全方位展示国内音乐教育的现状；反映教师的心声；推广、交流国内外先进的音乐教学方法；丰富了学生的音乐生活。");
        publicationRepository.save(p45);

        var p46 = new Publication();
        p46.setName("中小学教材教学");
        p46.setIssn("ISSN 2095-9338");
        p46.setCategory("教科文艺");
        p46.setPublicationFrequency("月刊");
        p46.setLanguage("中文");
        p46.setOrganizer("中国教育出版传媒集团有限公司");
        p46.setInfo("《中小学教材教学》杂志于2015年创刊，本刊是面向广大教研人员和中小学教师的专业性杂志，是广大教师交流教学思想、切磋教学方法的园地。帮助广大教师明确信课标、新大纲、新教材的指导思想和编写意图，树立新的教育教学理念，掌握新的教学方法，从而提高教学效率。");
        publicationRepository.save(p46);

        var p47 = new Publication();
        p47.setName("当代财经");
        p47.setIssn("ISSN 1005-0892");
        p47.setCategory("经济管理");
        p47.setPublicationFrequency("月刊");
        p47.setLanguage("中文");
        p47.setOrganizer("江西财经大学");
        p47.setInfo("《当代财经》杂志社创刊以来，坚持\"求实、创新、争鸣、服务\"的办刊宗旨，实施新世纪精品战略提高学术水平，为广大作者提供更好的展示才干的空间，在内容上追踪学术前沿，企求理论深度，突出当代财经的热点、难点和焦点。《当代财经》编辑部理论联系实践，强化精品意识、服务意识，多出理论精品，多树理论新识，为繁荣经济学术理论研究作出贡献。");
        publicationRepository.save(p47);

        var p48 = new Publication();
        p48.setName("中国土地科学");
        p48.setIssn("ISSN 1001-8158");
        p48.setCategory("经济管理");
        p48.setPublicationFrequency("月刊");
        p48.setLanguage("中文");
        p48.setOrganizer("中国土地学会;中国土地勘测规划院");
        p48.setInfo("《中国土地科学》杂志社创刊以来，全力推介国内外土地科学最新研究成果，快速报道国内外土地科学创新技术和方法，全面反映国内外土地科学学术思想和观点，推进土地科学研究和技术创新，支持土地资源科学利用和管护工作。《中国土地科学》编辑部融指导性、实用性、知识性于一体，以新观点、新方法、新材料为主题，坚持\"期期精彩、篇篇可读\"的理念，不断提高刊物的整体质量，颇受业界和广大读者的关注和好评。");
        publicationRepository.save(p48);

        var p49 = new Publication();
        p49.setName("现代农机");
        p49.setIssn("ISSN 1674-5604");
        p49.setCategory("农业科技");
        p49.setPublicationFrequency("双月刊");
        p49.setLanguage("中文");
        p49.setOrganizer("浙江省农机管理局;浙江万里学院");
        p49.setInfo("《现代农机》杂志社在20余年的办刊历程中，一贯遵循“普及农村机械和电器科技知识，提高农机人员技术素质，促进农机化事业发展”的办刊宗旨，紧扣农机化技术的发展形势，围绕读者需求，精心选题编辑，逐渐形成了自身的办刊特色——实用性、普及性、科学性相结合，突出实用性。刊物全面分析、报道机电领域的行业动态、先进技术、市场趋势及供求信息。");
        publicationRepository.save(p49);

        var p50 = new Publication();
        p50.setName("乡村科技");
        p50.setIssn("ISSN 1674-7909");
        p50.setCategory("农业科技");
        p50.setPublicationFrequency("旬刊");
        p50.setLanguage("中文");
        p50.setOrganizer("河南省科学技术信息研究院");
        p50.setInfo("乡村科技》杂志社以服务农村、农业、农民为宗旨，以实际、实用、实效为特色，以农村基层政府工作人员、农业科技人员、广大农民和各类农村专业户为读者对象，推广先进适用技术，推荐新优品种，介绍致富经验，传递农业信息，普及科技知识，融科学性、实用性、通俗性、趣味性为一体，是新时期农民的优秀科普读物。");
        publicationRepository.save(p50);

        // ↑ 在上面继续添加

        /*
         * 每新建保存一个期刊的对象,
         * 必须将期刊的封面图片下载下来,
         * 下载到 /src/main/resources/image/publicationCover 目录下,
         * 命名规范为 img-[期刊名].jpg
         * 这样才能依据期刊名自动找到要设置的封面图片
         */

        var savedPublications = publicationRepository.findAll();
        for (var publication : savedPublications) {
            // 期刊名
            var name = publication.getName();
            // 封面文件名
            var coverFileName = "img-" + name + ".jpg";
            var coverFilePath = "/image/publicationCover/" + coverFileName;
            // 取得文件对象
            var classPathResource = new ClassPathResource(coverFilePath);
            try (var inputStream = classPathResource.getInputStream()) {
                // 读取图片文件
                var bytes = inputStream.readAllBytes();
                var bytesFile = new BytesFile();
                bytesFile.setFileName(coverFileName);
                bytesFile.setContentType(MediaType.IMAGE_JPEG_VALUE);
                bytesFile.setSize((long) bytes.length);
                bytesFile.setBytes(bytes);
                fileRepository.save(bytesFile);
                // 设置封面
                publication.setCoverPicture(bytesFile);
                // 保存设置了封面的新期刊对象
                publicationRepository.save(publication);
            }
        }

    }

    /**
     * 向数据库插入新的用户
     */
    @Transactional
    public void insertNewUsers() {
        var user = new User();
        user.setUsername("user");
        user.setName("user");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(Role.USER);
        user.setPhone("12345611111");
        user.setEmail("");
        user.setAge(20);
        user.setGraduatedSchool("西安石油大学");
        user.setEducationalBackground("本科");
        user.setContactAddress("");
        user.setGender("男");
        user.setZipCode("414000");
        user.setEmail("111@11.com");
        userRepository.save(user);

        var admin = new User();
        admin.setUsername("admin");
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setRole(Role.ADMIN);
        admin.setPhone("12345622222");
        user.setEmail("");
        admin.setGender("女");
        user.setEducationalBackground("博士");
        user.setGraduatedSchool("西安石油大学");
        user.setContactAddress("");
        user.setAge(23);
        user.setZipCode("414000");
        user.setEmail("222@11.com");
        userRepository.save(admin);

        var editor = new User();
        editor.setUsername("editor");
        editor.setName("editor");
        user.setEmail("");
        editor.setPassword(passwordEncoder.encode("123456"));
        editor.setRole(Role.EDITOR);
        editor.setPhone("12345622222");
        user.setGraduatedSchool("西安石油大学");
        user.setContactAddress("");
        user.setZipCode("710300");
        editor.setGender("女");
        user.setEmail("333@11.com");
        userRepository.save(editor);
    }

}
