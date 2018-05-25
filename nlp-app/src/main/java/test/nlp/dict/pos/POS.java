package test.nlp.dict.pos;

import java.util.Set;

import test.nlp.ltp.LTPPOS;

/**
 * 词性类
 */
public enum POS {

    /**
     * 形容词
     */
    a() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.a;
        }
    },

    /**
     * 副形词
     */
    ad() {
        @Override
        public POS getParent() {
            return a;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.a;
        }
    },

    /**
     * 形容词性语素
     */
    ag() {
        @Override
        public POS getParent() {
            return a;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.g;
        }
    },

    /**
     * 形容惯用语
     */
    al() {
        @Override
        public POS getParent() {
            return a;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.a;
        }
    },

    /**
     * 名形词
     */
    an() {
        @Override
        public POS getParent() {
            return a;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.a;
        }
    },

    /**
     * 状态形容词
     */
    az() {
        @Override
        public POS getParent() {
            return a;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.a;
        }
    },

    /**
     * 区别词
     */
    b() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.b;
        }
    },

    /**
     * 区别词性惯用语
     */
    bl() {
        @Override
        public POS getParent() {
            return b;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.b;
        }

    },

    /**
     * 连词
     */
    c() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.c;
        }
    },

    /**
     * 并列连词
     */
    cc() {
        @Override
        public POS getParent() {
            return c;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.c;
        }
    },

    /**
     * 副词
     */
    d() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.d;
        }
    },

    /**
     * 副词性语素
     */
    dg() {
        @Override
        public POS getParent() {
            return d;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.g;
        }
    },

    /**
     * 副词性惯用语
     */
    dl() {
        @Override
        public POS getParent() {
            return d;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.d;
        }

    },

    /**
     * 叹词
     */
    e() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.e;
        }
    },

    /**
     * 方位词
     */
    f() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.nd;
        }
    },

    /**
     * 语素词
     */
    g() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.g;
        }
    },

    /**
     * 前缀
     */
    h() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.h;
        }
    },

    /**
     * 成语
     */
    i() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.i;
        }
    },

    /**
     * 简称
     */
    j() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.j;
        }
    },

    /**
     * 后缀
     */
    k() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.k;
        }
    },

    /**
     * 习用语
     */
    l() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.i;
        }
    },

    /**
     * 数词
     */
    m() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.m;
        }
    },

    /**
     * 数量词
     */
    mq() {
        @Override
        public POS getParent() {
            return m;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.m;
        }
    },

    /**
     * 名词
     */
    n() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }
    },

    /**
     * 名词性语素
     */
    ng() {
        @Override
        public POS getParent() {
            return n;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.g;
        }
    },

    /**
     * 医药疾病等健康相关名词
     */
    nh() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

        @Override
        public POS getParent() {
            return n;
        }
    },

    /**
     * 疾病
     */
    nhd() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

        @Override
        public POS getParent() {
            return nh;
        }

    },

    /**
     * 药品
     */
    nhm() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

        @Override
        public POS getParent() {
            return nh;
        }

    },

    /**
     * 机构后缀
     */
    nis() {

        @Override
        public POS getParent() {
            return n;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }
    },

    /**
     * 名词性惯用语
     */
    nl() {
        @Override
        public POS getParent() {
            return n;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

    },

    /**
     * 工作相关名词
     */
    nn() {
        @Override
        public POS getParent() {
            return n;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

    },

    /**
     * 职务职称
     */
    nnt() {

        @Override
        public POS getParent() {
            return nn;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }
    },

    /**
     * 职业
     */
    nnd() {
        @Override
        public POS getParent() {
            return nn;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }
    },

    /**
     * 人名
     */
    nr() {
        public POS getParent() {
            return n;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.nh;
        }
    },

    /**
     * 汉语姓氏
     */
    nr1() {
        @Override
        public POS getParent() {
            return nr;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.nh;
        }
    },

    /**
     * 汉语名字
     */
    nr2() {
        @Override
        public POS getParent() {
            return nr;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.nh;
        }
    },

    /**
     * 音译人名
     */
    nrf() {
        @Override
        public POS getParent() {
            return nr;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.nh;
        }

    },

    /**
     * 日本人名
     */
    nrj() {
        @Override
        public POS getParent() {
            return nr;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.nh;
        }
    },

    /**
     * 地名
     */
    ns() {
        @Override
        public POS getParent() {
            return n;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ns;
        }

    },

    /**
     * 国家名
     */
    nsc1() {
        @Override
        public POS getParent() {
            return ns;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ns;
        }
    },

    /**
     * 省、行政区名
     */
    nsp {
        @Override
        public POS getParent() {
            return ns;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ns;
        }
    },

    /**
     * 城市名
     */
    nsc2 {
        @Override
        public POS getParent() {
            return ns;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ns;
        }

    },

    /**
     * 县区、自治州名
     */
    nst {
        @Override
        public POS getParent() {
            return ns;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ns;
        }
    },

    /**
     * 音译地名
     */
    nsf() {
        @Override
        public POS getParent() {
            return ns;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ns;
        }

    },

    /**
     * 机构名
     */
    nt() {
        @Override
        public POS getParent() {
            return n;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ni;
        }
    },

    /**
     * 医院
     */
    nth() {

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ni;
        }

        @Override
        public POS getParent() {
            return nt;
        }
    },

    /**
     * 幼儿园
     */
    ntk() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ni;
        }

        @Override
        public POS getParent() {
            return nt;
        }
    },

    /**
     * 政府机构
     */
    nto() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ni;
        }

        @Override
        public POS getParent() {
            return nt;
        }
    },

    /**
     * 中小学
     */
    nts() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ni;
        }

        @Override
        public POS getParent() {
            return nt;
        }
    },

    /**
     * 大学
     */
    ntu() {

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ni;
        }

        @Override
        public POS getParent() {
            return nt;
        }
    },

    /**
     * 英文串
     */
    nx() {
        @Override
        public POS getParent() {
            return n;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ws;
        }
    },

    /**
     * 其他专名
     */
    nz() {
        @Override
        public POS getParent() {
            return n;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.nz;
        }
    },

    /**
     * 拟声词
     */
    o() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.o;
        }
    },

    /**
     * 介词
     */
    p() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.p;
        }
    },

    /**
     * 把
     */
    pba() {
        @Override
        public POS getParent() {
            return p;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.p;
        }
    },

    /**
     * 被
     */
    pbei() {
        @Override
        public POS getParent() {
            return p;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.p;
        }
    },

    /**
     * 量词
     */
    q() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.q;
        }
    },

    /**
     * 时量词
     */
    qt() {
        @Override
        public POS getParent() {
            return q;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.q;
        }
    },

    /**
     * 动量词
     */
    qv() {
        @Override
        public POS getParent() {
            return q;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.q;
        }
    },

    /**
     * 代词
     */
    r() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.r;
        }
    },

    /**
     * 代词性语素
     */
    rg() {
        @Override
        public POS getParent() {
            return r;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.g;
        }
    },

    /**
     * 人称代词
     */
    rr() {
        @Override
        public POS getParent() {
            return r;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.r;
        }
    },

    /**
     * 疑问代词
     */
    ry() {
        @Override
        public POS getParent() {
            return r;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.r;
        }
    },

    /**
     * 处所疑问代词
     */
    rys() {
        @Override
        public POS getParent() {
            return ry;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.r;
        }

    },

    /**
     * 时间疑问代词
     */
    ryt() {
        @Override
        public POS getParent() {
            return ry;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.r;
        }

    },

    /**
     * 谓词性疑问代词
     */
    ryv() {
        @Override
        public POS getParent() {
            return ry;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.r;
        }

    },

    /**
     * 指示代词
     */
    rz() {
        @Override
        public POS getParent() {
            return r;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.r;
        }
    },

    /**
     * 处所指示代词
     */
    rzs() {
        @Override
        public POS getParent() {
            return rz;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.r;
        }
    },

    /**
     * 时间指示代词
     */
    rzt() {
        @Override
        public POS getParent() {
            return rz;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.r;
        }
    },

    /**
     * 谓词性指示代词
     */
    rzv() {
        @Override
        public POS getParent() {
            return rz;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.r;
        }

    },

    /**
     * 处所词
     */
    s() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.nl;
        }
    },

    /**
     * 时间词
     */
    t() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.nt;
        }
    },

    /**
     * 时间词性语素
     */
    tg() {
        @Override
        public POS getParent() {
            return t;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.g;
        }

    },

    /**
     * 助词
     */
    u() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 的 底
     */
    ude1() {
        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 地
     */
    ude2() {

        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 得
     */
    ude3() {
        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 等 等等 云云
     */
    udeng() {
        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 的话
     */
    udh() {
        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 过
     */
    uguo() {
        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 了 喽
     */
    ule() {
        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 连 （“连小学生都会”）
     */
    ulian() {
        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 来讲 来说 而言 说来
     */
    uls() {
        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 所
     */
    usuo() {
        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 一样 一般 似的 般
     */
    uyy() {
        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 着
     */
    uzhe() {
        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }

    },

    /**
     * 之
     */
    uzhi() {
        @Override
        public POS getParent() {
            return u;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 动词
     */
    v() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.v;
        }
    },

    /**
     * 副动词
     */
    vd() {
        @Override
        public POS getParent() {
            return v;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.v;
        }
    },

    /**
     * 趋向动词
     */
    vf() {
        @Override
        public POS getParent() {
            return v;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.v;
        }
    },

    /**
     * 动词性语素
     */
    vg() {
        @Override
        public POS getParent() {
            return v;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.g;
        }
    },

    /**
     * 不及物动词
     */
    vi() {
        @Override
        public POS getParent() {
            return v;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.v;
        }
    },

    /**
     * 动词性惯用词
     */
    vl() {
        @Override
        public POS getParent() {
            return v;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.v;
        }
    },

    /**
     * 名动词
     */
    vn() {
        @Override
        public POS getParent() {
            return v;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.v;
        }
    },

    /**
     * 动词“是”
     */
    vshi() {
        @Override
        public POS getParent() {
            return v;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.v;
        }
    },

    /**
     * 能愿动词
     */
    vu() {
        @Override
        public POS getParent() {
            return v;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.v;
        }
    },

    /**
     * 形式动词
     */
    vx() {
        @Override
        public POS getParent() {
            return v;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.v;
        }
    },

    /**
     * 动词“有”
     */
    vyou() {
        @Override
        public POS getParent() {
            return v;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.v;
        }
    },

    /**
     * 标点符号
     */
    w() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 百分号千分号，全角：％ ‰ 半角：%
     */
    wb() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 逗号，全角：， 半角：,
     */
    wd() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 分号，全角：； 半角： ;
     */
    wf() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }

    },

    /**
     * 单位符号，全角：￥ ＄ ￡ ° ℃ 半角：$
     */
    wh() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 句号，全角：。
     */
    wj() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 右括号，全角：） 〕 ］ ｝ 》 】 〗 〉 半角： ) ] { >
     */
    wky() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }

    },

    /**
     * 左括号，全角：（ 〔 ［ ｛ 《 【 〖 〈 半角：( [ { <
     */
    wkz() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 冒号，全角：： 半角： :
     */
    wm() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 顿号，全角：、
     */
    wn() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 破折号，全角：—— －－ ——－ 半角：--- ----
     */
    wp() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 省略号，全角：…… …
     */
    ws() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 叹号，全角：！ 半角：!
     */
    wt() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 右引号，全角：” ’ 』
     */
    wyy() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 左引号，全角：“ ‘ 『
     */
    wyz() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 问号，全角：？ 半角：?
     */
    ww() {
        @Override
        public POS getParent() {
            return w;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.wp;
        }
    },

    /**
     * 字符串
     */
    x() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ws;
        }
    },

    /**
     * Email字符串
     */
    xe() {
        @Override
        public POS getParent() {
            return x;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ws;
        }
    },

    /**
     * 网址URL
     */
    xu() {
        @Override
        public POS getParent() {
            return x;
        }

        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.ws;
        }
    },

    /**
     * 语气词
     */
    y() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.u;
        }
    },

    /**
     * 仅适用于“始##始”
     */
    begin() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.begin;
        }

    },

    /**
     * 仅适用于“末##末”
     */
    end() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.end;
        }
    },

    // index

    /**
     * 检查
     */
    nhc() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

        @Override
        public POS getParent() {
            return nh;
        }

    },

    /**
     * 器械
     */
    nhi() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

        @Override
        public POS getParent() {
            return nh;
        }

    },

    /**
     * 手术
     */
    nhsu() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

        @Override
        public POS getParent() {
            return nh;
        }

    },

    /**
     * 症状
     */
    nhsy() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

        @Override
        public POS getParent() {
            return nh;
        }

    },

    /**
     * 中医方剂
     */
    nhzyfj() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

        @Override
        public POS getParent() {
            return nh;
        }

    },

    /**
     * 中医穴位
     */
    nhzyxw() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

        @Override
        public POS getParent() {
            return nh;
        }

    },

    /**
     * 中医药材
     */
    nhzyyc() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

        @Override
        public POS getParent() {
            return nh;
        }

    },

    /**
     * 身体部位
     */
    nhb() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

        @Override
        public POS getParent() {
            return nh;
        }

    },

    /**
     * 药物化学中文名称(WHO：p-INN & r-INN)
     */
    nhinn() {
        @Override
        public LTPPOS getLTPPOS() {
            return LTPPOS.n;
        }

        @Override
        public POS getParent() {
            return nh;
        }

    },
    ;

    /**
     * 返回上一级词性
     * 
     * @return
     */
    public POS getParent() {
        return null;
    }

    /**
     * 返回对应的LTP词性
     * 
     * @return
     */
    public abstract LTPPOS getLTPPOS();

    /**
     * 判断当前词性是否是给定词性的子类
     * 
     * @param pos
     * @return
     */
    public boolean isBelong(POS pos) {
        POS temp = this;
        boolean isBelong = false;
        while (temp != null) {
            if (temp == pos) {
                isBelong = true;
                break;
            }
            temp = temp.getParent();

        }
        return isBelong;
    }

    /**
     * 判断当前词性是否是给定词性集的子类
     * 
     * @param curPOS
     *            当前词性
     * @param poses
     *            给定的词性集
     * @return
     */
    public boolean isBelong(Set<POS> poses) {
        POS temp = this;
        boolean isBelong = false;
        while (temp != null) {
            if (poses.contains(temp)) {
                isBelong = true;
                break;
            }
            temp = temp.getParent();

        }
        return isBelong;
    }

    public static void main(String[] args) {
        System.out.println(POS.end.ordinal());
        System.out.println(POS.values().length);
    }
}
