import React, { useContext, useEffect, useMemo, useState } from 'react';
import Header from '../components/landmark/Header';
import { Link } from 'react-router-dom';
import { AppContext } from '../main';
import ContextLogo from '../types/ContextLogo';
import Main from '../components/landmark/Main';
import Toolbar from '../components/landmark/Toolbar';
import TabButtonComponent from '../components/common/TabButton';
import Button from '../components/BaseButton';
import Layout from '../components/Layout';
import LabelList from '../components/LabelList';
import axios from '../api/axios';

enum Option {
  label,
  milestone,
}

const { label, milestone } = Option;

const TabButton = React.memo(TabButtonComponent);

export default function Options() {
  const { util } = useContext(AppContext);
  const [activeOption, setActiveOption] = useState<Option>(label);
  const [labels, setLabels] = useState([]);
  const [milestones, setMilestones] = useState([]);
  const labelFetch = () => axios.get('api/label');

  const milestoneFetch = () => axios.get('api/milestone');

  useEffect(() => {
    (async () => {
      const fetch = activeOption === label ? labelFetch : milestoneFetch;
      const res = await fetch();
      if (activeOption === label) {
        setLabels(res.data);
      } else {
        setMilestones(res.data);
      }
    })();
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
                event: () => setActiveOption(label),
              },
              {
                iconName: 'milestone',
                text: '마일스톤',
                event: () => setActiveOption(milestone),
              },
            ]}
          />
          <Button type="button" flexible iconName="plus">
            레이블 추가
          </Button>
        </Toolbar>
        {activeOption === label ? <>라벨</> : <>마일스톤</>}
      </Main>
    </Layout>
  );
}
