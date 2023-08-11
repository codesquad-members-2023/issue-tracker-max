import React, { useContext, useEffect, useState } from 'react';
import Header from '../components/landmark/Header';
import { Link } from 'react-router-dom';
import { AppContext } from '../main';
import ContextLogo from '../types/ContextLogo';
import Main from '../components/landmark/Main';
import Toolbar from '../components/landmark/Toolbar';
import TabButtonComponent from '../components/common/TabButton';
import Layout from '../components/Layout';
import axios from '../api/axios';
import Button from '../components/common/button/BaseButton';
import Labels from '../components/Labels';

enum Option {
  labels,
  milestones,
}

const { labels, milestones } = Option;

const TabButton = React.memo(TabButtonComponent);

export default function Options() {
  const { util } = useContext(AppContext);
  const [activeOption, setActiveOption] = useState<Option>(labels);
  const [labelData, setLabelDatas] = useState([
    {
      id: 1,
      textColor: 'AAA333',
      backgroundColor: 'FFF3FF',
      name: 'feat',
      description: '설명임',
    },
  ]);
  // const [milestones, setMilestones] = useState([]);

  useEffect(() => {
    return () => {
      (async () => {
        const headers = {
          Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
        };
        const fetch =
          activeOption === labels
            ? () => axios.get('api/labels', { headers })
            : () => axios.get('api/milestones', { headers });

        const res = await fetch();
        console.log(res.data);
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
