import React, { useContext, useEffect, useState } from 'react';
import Header from '../components/landmark/Header';
import { Link } from 'react-router-dom';
import { AppContext } from '../main';
import ContextLogo from '../types/ContextLogo';
import Main from '../components/landmark/Main';
import Toolbar from '../components/landmark/Toolbar';
import TabButtonComponent from '../components/common/TabButton';
import Layout from '../components/Layout';
import Button from '../components/common/button/BaseButton';
import Labels from '../components/label/Labels';
import useAxiosPrivate from '../hooks/useAxiosPrivate';

enum Option {
  labels,
  milestones,
}

const { labels, milestones } = Option;

const TabButton = React.memo(TabButtonComponent);

export default function Options() {
  const { util } = useContext(AppContext);
  const [activeOption, setActiveOption] = useState<Option>(labels);
  const [labelData, setLabelDatas] = useState([]);
  // const [milestones, setMilestones] = useState([]);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    return () => {
      (async () => {
        const fetchData =
          activeOption === labels
            ? () => axiosPrivate.get('api/labels')
            : () => axiosPrivate.get('api/milestones');

        const res = await fetchData();

        if (activeOption === labels) {
          setLabelDatas(res.data.message.labels);
        } else {
          // setMilestones(res.data);
        }
      })();
    };
  }, [activeOption]);

  return (
    <Layout>
      <Header>
        <Link to="/">
          <img
            src={(util.getLogoByTheme() as ContextLogo).medium}
            alt="이슈트래커"
          />
        </Link>
        <div>
          {/* profile */}
          프로파일이 들어갈 부분
        </div>
      </Header>
      <Main>
        <Toolbar>
          <TabButton
            tabs={[
              {
                iconName: 'label',
                text: '레이블',
                event: () => setActiveOption(labels),
              },
              {
                iconName: 'milestone',
                text: '마일스톤',
                event: () => setActiveOption(milestones),
              },
            ]}
          />
          <Button type="button" flexible iconName="plus">
            레이블 추가
          </Button>
        </Toolbar>
        {activeOption === labels ? <Labels data={labelData} /> : <>마일스톤</>}
      </Main>
    </Layout>
  );
}
