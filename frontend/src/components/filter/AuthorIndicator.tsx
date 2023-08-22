import { useEffect, useState } from 'react';
import { styled } from 'styled-components';
import DropdownIndicator from '../common/DropdownIndicator';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import DropdownPanelElement from '../../types/DropdownPanelElement';
import Option from '../../constant/Option';
import DropdownType from '../../constant/DropdownType';
import Participant from '../../types/Participant';

export default function AuthorIndicator() {
  const [authors, setAuthors] = useState<Participant[]>([]);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    async function fetchData() {
      const res = await axiosPrivate.get('/api/issues/participants');
      const { participants } = res.data.message;
      try {
        setAuthors(participants);
      } catch (error) {
        console.error(error);
      }
    }

    fetchData();
  }, [axiosPrivate]);

  return (
    <Container>
      <h3 className="blind">작성자</h3>
      <DropdownIndicator
        type={DropdownType.author}
        text="작성자"
        label="작성자 필터"
        elements={authorsToElements(authors)}
      />
    </Container>
  );
}

const Container = styled.section``;

function authorsToElements(participants: Participant[]) {
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
