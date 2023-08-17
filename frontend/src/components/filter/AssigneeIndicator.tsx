import { useEffect, useState } from 'react';
import { styled } from 'styled-components';
import DropdownIndicator from '../common/DropdownIndicator';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import DropdownPanelElement from '../../types/DropdownPanelElement';
import Option from '../../constant/Option';
import DropdownType from '../../constant/DropdownType';
import Participant from '../../types/Participant';

export default function AssigneeIndicator() {
  const [participants, setParticipants] = useState<Participant[]>([]);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    async function fetchData() {
      const res = await axiosPrivate.get('/api/issues/participants');
      const { participants } = res.data.message;
      try {
        setParticipants(participants);
      } catch (error) {
        console.error(error);
      }
    }

    fetchData();
  }, [axiosPrivate]);

  return (
    <Container>
      <h3 className="blind">담당자</h3>
      <DropdownIndicator
        type={DropdownType.assignee}
        text="담당자"
        label="담당자 필터"
        elements={participantsToElements(participants)}
      />
    </Container>
  );
}

const Container = styled.section``;

function participantsToElements(participants: Participant[]) {
  const elements: DropdownPanelElement<DropdownType.assignee>[] = [];
  participants.forEach(({ name, imageUrl }) =>
    elements.push({
      type: DropdownType.assignee,
      text: name,
      option: Option.Available,
      imageUrl,
    })
  );

  return elements;
}
