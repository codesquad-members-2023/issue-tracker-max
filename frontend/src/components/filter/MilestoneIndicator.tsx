import { useEffect, useState } from 'react';
import { styled } from 'styled-components';
import DropdownIndicator from '../common/DropdownIndicator';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import DropdownPanelElement from '../../types/DropdownPanelElement';
import { Milestone } from '../../types';
import Option from '../../constant/Option';
import DropdownType from '../../constant/DropdownType';

export default function MilestoneIndicator() {
  const [milestones, setMilestones] = useState<Milestone[]>([]);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    async function fetchData() {
      try {
        const res = await axiosPrivate.get('api/assignees');
        const { milestones } = res.data.message;
        setMilestones(milestones);
      } catch (error) {
        console.error(error);
      }
    }

    fetchData();
  }, [axiosPrivate]);

  return (
    <Container>
      <h3 className="blind">마일스톤</h3>
      <DropdownIndicator
        type={DropdownType.milestone}
        text="마일스톤"
        label="마일스톤 필터"
        elements={milestonesToElements(milestones)}
      />
    </Container>
  );
}

const Container = styled.section``;

function milestonesToElements(milestones: Milestone[]) {
  const elements: DropdownPanelElement<DropdownType.milestone>[] = [];
  milestones.forEach(({ id, name }) =>
    elements.push({
      type: DropdownType.milestone,
      text: name,
      option: Option.Available,
      id,
      name,
    })
  );

  return elements;
}
